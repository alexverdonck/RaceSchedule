package com.github.alexverdonck.raceschedule.eventtracker

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.github.alexverdonck.raceschedule.data.Event
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

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
        text = item.sessions["Race"].toString()// need to format this for time/countdown/live/localtime stuff
    }
}

@BindingAdapter("eventSession")
fun TextView.setEventSession(item: String?) {
    item?.let {
        text = item// change, need to display each session
    }
}