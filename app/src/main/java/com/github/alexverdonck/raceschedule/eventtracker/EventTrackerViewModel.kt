package com.github.alexverdonck.raceschedule.eventtracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.github.alexverdonck.raceschedule.R
import com.github.alexverdonck.raceschedule.data.Event
import com.github.alexverdonck.raceschedule.data.Events
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

@OptIn(ExperimentalSerializationApi::class)
class EventTrackerViewModel(application: Application) : AndroidViewModel(application) {

    private fun getEventsJson(): InputStream {
        return getApplication<Application>().applicationContext.resources.openRawResource(R.raw.events)
    }

    init {
        try {
            val res: InputStream = getEventsJson()
            val inputStreamReader = InputStreamReader(res)
            val sb = StringBuilder()
            var line: String?
            val br = BufferedReader(inputStreamReader)
            line = br.readLine()
            while (line != null) {
                sb.append(line)
                line = br.readLine()
            }

            Events.events.value = Json.decodeFromString(sb.toString())

        } catch (ex: Exception) {
            println(ex.message)
        }
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