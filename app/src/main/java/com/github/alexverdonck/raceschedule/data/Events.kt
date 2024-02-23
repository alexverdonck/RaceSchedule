package com.github.alexverdonck.raceschedule.data

import androidx.lifecycle.MutableLiveData
import java.time.OffsetDateTime

object Events {
    var events = MutableLiveData<List<Event>>()
}

fun MutableLiveData<List<Event>>.next(): Event? {
    return this.value?.firstOrNull { event -> event.sessions.any{session -> session.value != null &&
            session.value!!.isAfter(OffsetDateTime.now(session.value?.offset))} }
}
