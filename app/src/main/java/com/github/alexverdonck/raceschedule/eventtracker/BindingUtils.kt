package com.github.alexverdonck.raceschedule.eventtracker

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.github.alexverdonck.raceschedule.data.Event
import com.github.alexverdonck.raceschedule.data.nextSession
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMM yyyy h:mm a")

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