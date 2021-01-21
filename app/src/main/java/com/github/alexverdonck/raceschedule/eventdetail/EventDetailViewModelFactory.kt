package com.github.alexverdonck.raceschedule.eventdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.alexverdonck.raceschedule.data.Event
import java.lang.IllegalArgumentException

class EventDetailViewModelFactory(private val selectedEvent: Event) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventDetailViewModel::class.java)) {
            return EventDetailViewModel(selectedEvent) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}