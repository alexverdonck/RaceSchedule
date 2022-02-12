package com.github.alexverdonck.raceschedule.eventtracker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class EventTrackerViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventTrackerViewModel::class.java)) {
            return EventTrackerViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}