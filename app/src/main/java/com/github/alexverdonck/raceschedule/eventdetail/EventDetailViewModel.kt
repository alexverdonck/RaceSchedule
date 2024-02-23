package com.github.alexverdonck.raceschedule.eventdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.alexverdonck.raceschedule.data.Event

class EventDetailViewModel(selectedEvent: Event) : ViewModel() {

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event>
        get() = _event

    init {
        _event.value = selectedEvent
    }



}