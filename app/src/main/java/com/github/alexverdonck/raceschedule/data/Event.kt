package com.github.alexverdonck.raceschedule.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.time.DurationUnit
import kotlin.time.toDuration

// TODO need to allow session time to be nullable to allow for TBC dates
@Serializable
@Parcelize
data class Event(
    val location: String?,
    val name: String?,
    val sessions: Map<String, @Serializable(OffsetDateTimeSerializer::class) OffsetDateTime>
) :
    Parcelable

// TODO clean up all this date calculation formatting etc, don't show Days/hours if they are 0
fun Event.nextSession(): String {
    val raceTime = sessions["Race"]
    val currentTime = OffsetDateTime.now(raceTime?.offset)
    if (raceTime?.plusHours(2)?.isBefore(currentTime) == true) {
        return "Race over"
    }

    var nextSession: OffsetDateTime? = null
    for (session in sessions) {
        if (session.value > currentTime) {
            nextSession = session.value
            break
        }
    }

    if (nextSession == null) {
        return "LIVE!"
    }

    val timeUntil = OffsetDateTime.now(nextSession.offset)
        .until(nextSession, ChronoUnit.MINUTES)

    val duration = timeUntil.toDuration(DurationUnit.MINUTES)
    val d = duration.inWholeDays
    val h = duration.inWholeHours % 24
    val m = duration.inWholeMinutes % 60

    if (d < 32) {
        return "$d days $h hours $m minutes"
    }
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMM yyyy h:mm a")
    return nextSession.format(formatter.withZone(ZoneId.systemDefault()))
}


object OffsetDateTimeSerializer : KSerializer<OffsetDateTime> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("OffsetDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: OffsetDateTime) {
        val format = DateTimeFormatter.ISO_OFFSET_DATE_TIME

        val string = format.format(value)
        encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): OffsetDateTime {
        val string = decoder.decodeString()
        return OffsetDateTime.parse(string)
    }
}
