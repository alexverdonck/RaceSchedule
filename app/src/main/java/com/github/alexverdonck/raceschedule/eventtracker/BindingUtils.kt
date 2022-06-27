package com.github.alexverdonck.raceschedule.eventtracker

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.github.alexverdonck.raceschedule.data.Event
import com.github.alexverdonck.raceschedule.data.nextSession
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.time.DurationUnit
import kotlin.time.toDuration

val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMM h:mm a")

@BindingAdapter("eventName")
fun TextView.setEventName(item: Event?) {
    item?.let {
        text = item.name
    }
}

@BindingAdapter("eventLocation")
fun TextView.setEventLocation(item: Event?) {
    item?.let {
        text = item.location
    }
}

@BindingAdapter("eventRaceTime")
fun TextView.setEventTime(item: Event?) {
    item?.let {
        text = item.sessions["Race"]?.format(formatter.withZone(ZoneId.systemDefault()))
    }
}

@BindingAdapter("eventNextSession")
fun TextView.setEventNextSession(item: Event?) {
    item?.let {
        text = item.nextSession()
    }
}

@BindingAdapter("eventSessionTime")
fun TextView.setEventSessionTime(item: Pair<String, OffsetDateTime?>?) {
    item?.let {
        text = if (item.second == null) {
            "TBC"
        } else {
            item.second!!.format(formatter.withZone(ZoneId.systemDefault()))
        }

    }
}

@BindingAdapter("eventSessionName")
fun TextView.setEventSessionName(item: Pair<String, OffsetDateTime?>?) {
    item?.let {
        text = item.first
    }
}

@BindingAdapter("eventSessionCountdown")
fun TextView.setSessionCountdown(item: Pair<String, OffsetDateTime?>?)
{
    item?.let {
        text = sessionCountdown(item.second)
    }
}

// todo cleanup
fun sessionCountdown(sessionTime: OffsetDateTime?): String {
    if (sessionTime == null)
    {
        return ""
    }
    val currentTime = OffsetDateTime.now(sessionTime?.offset)
    if (sessionTime?.plusHours(2)?.isBefore(currentTime)!!) {
        return "Complete"
    } else if (sessionTime?.isBefore(currentTime)) {
        return "Live"
    } else {
        val timeUntil = OffsetDateTime.now(sessionTime?.offset)
            .until(sessionTime, ChronoUnit.MINUTES)

        val duration = timeUntil.toDuration(DurationUnit.MINUTES)
        val d = duration.inWholeDays
        val h = duration.inWholeHours % 24
        val m = duration.inWholeMinutes % 60

        if (d < 7) {
            return "$d days $h hours $m minutes"
        }
    }
    return ""
}
