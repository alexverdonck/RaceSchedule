package com.github.alexverdonck.raceschedule.eventtracker

import android.app.Application
import android.content.Context
import androidx.annotation.RawRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.github.alexverdonck.raceschedule.R
import com.github.alexverdonck.raceschedule.data.Event
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class EventTrackerViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    val events = MutableLiveData<List<Event>>()
    fun Context.getRawInput(@RawRes resourceId: Int): InputStream {
        return resources.openRawResource(resourceId)
    }
    init {  // add item id
        try {
            val res: InputStream = context.resources.openRawResource(R.raw.events)
            val inputStreamReader = InputStreamReader(res)
            val sb = StringBuilder()
            var line: String?
            val br = BufferedReader(inputStreamReader)
            line = br.readLine()
            while (line != null) {
                sb.append(line)
                line = br.readLine()
            }

            events.value = Json.decodeFromString(sb.toString())

        } catch (ex: Exception) {
            println(ex.message)
        }
    }

    // navigation stuff
    private val _navigateToEventDetail = MutableLiveData<Event>()
    val navigateToEventDetail
        get() = _navigateToEventDetail

    fun onEventClicked(event: Event) {
        _navigateToEventDetail.value = event
    }

    fun onEventDetailNavigated() {
        _navigateToEventDetail.value = null
    }
}