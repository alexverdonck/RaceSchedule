package com.github.alexverdonck.raceschedule.eventtracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.github.alexverdonck.raceschedule.R
import com.github.alexverdonck.raceschedule.data.Event
import com.github.alexverdonck.raceschedule.data.Events
import com.github.alexverdonck.raceschedule.utils.readJsonFromRaw
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
class EventTrackerViewModel(application: Application) : AndroidViewModel(application) {

    init {
        Events.events.value =
            readJsonFromRaw(getApplication<Application>().applicationContext, R.raw.events)
    }

    // navigation stuff
    private val _navigateToEventDetail = MutableLiveData<Event?>()
    val navigateToEventDetail
        get() = _navigateToEventDetail

    fun onEventClicked(event: Event) {
        _navigateToEventDetail.value = event
    }

    fun onEventDetailNavigated() {
        _navigateToEventDetail.value = null
    }
}