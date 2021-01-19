package com.github.alexverdonck.raceschedule.eventtracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.alexverdonck.raceschedule.data.Event
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class EventTrackerFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun getEvents(res: InputStream): ArrayList<Event> {
        val result = ArrayList<Event>()

        // build json string and store objects in list
        try {
            val inputStreamReader = InputStreamReader(res)
            val sb = StringBuilder()
            var line: String?
            val br = BufferedReader(inputStreamReader)
            line = br.readLine()
            while (line != null) {
                sb.append(line)
                line = br.readLine()
            }
            // change to array list for less code? or maybe just use list, check after displaying data in recyclerview
            var eventList: List<Event> = Json.decodeFromString(sb.toString())
            result.addAll(eventList)

        } catch (ex: Exception) {
            println(ex.message)
        }
        return result
    }
}