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
import java.lang.Exception
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
    val sessions: Map<String, @Serializable(OffsetDateTimeSerializer::class) OffsetDateTime?> // TODO session time may be a string if event is cancelled or TBA etc...
) :
    Parcelable

// TODO clean up all this date calculation formatting etc, don't show Days/hours if they are 0
fun Event.nextSession(): String {
    val raceTime = sessions["Race"] ?: return "TBC"
    val currentTime = OffsetDateTime.now(raceTime.offset)
    if (raceTime.plusHours(2)?.isBefore(currentTime) == true) {
        return "Race over"
    }

    var nextSessionTime: OffsetDateTime? = null
    var nextSessionName = ""
    for (session in sessions) {
        if (session.value != null) {
            if (session.value!! > currentTime) {
                nextSessionTime = session.value
                nextSessionName = session.key
                break
            }
        }
    }

    if (nextSessionTime == null) {
        return "LIVE!"
    }

    val timeUntil = OffsetDateTime.now(nextSessionTime.offset)
        .until(nextSessionTime, ChronoUnit.MINUTES)

    val duration = timeUntil.toDuration(DurationUnit.MINUTES)
    val d = duration.inWholeDays
    val h = duration.inWholeHours % 24
    val m = duration.inWholeMinutes % 60

    if (d < 30) {
        return "$nextSessionName\n$d days $h hours $m minutes"
    }
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMM h:mm a")
    val formattedTime = nextSessionTime.format(formatter.withZone(ZoneId.systemDefault()))
    return "$nextSessionName\n$formattedTime"
}

fun Event.raceSession(): String {
    val race = sessions["Race"] ?: throw Exception("Session does not have race time")
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("E d MMM h:mm a")

    return race.format(formatter.withZone(ZoneId.systemDefault()));
}

// TODO session time may be a string if event is cancelled or TBA etc...
object OffsetDateTimeSerializer : KSerializer<OffsetDateTime?> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("OffsetDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: OffsetDateTime?) {
        val format = DateTimeFormatter.ISO_OFFSET_DATE_TIME

        val string = format.format(value)
        encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): OffsetDateTime? {
        val string = decoder.decodeString()
        return try {
            OffsetDateTime.parse(string)
        } catch (e: Exception) {
            null
        }
    }
}
