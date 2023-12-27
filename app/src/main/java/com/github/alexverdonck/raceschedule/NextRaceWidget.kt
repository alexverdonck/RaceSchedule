package com.github.alexverdonck.raceschedule

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.github.alexverdonck.raceschedule.data.Events
import com.github.alexverdonck.raceschedule.data.next
import com.github.alexverdonck.raceschedule.data.raceSession
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Implementation of App Widget functionality.
 */
class NextRaceWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
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

            Events.events.value = Json.decodeFromString(sb.toString())

        } catch (ex: Exception) {
            println(ex.message)
        }
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val nextEvent = Events.events.next()
    var raceTime = ""
    var raceLocation = "Season Over"
    if (nextEvent != null) {
        raceLocation = nextEvent.location!!
        raceTime = nextEvent.raceSession()
    }
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.next_race_widget)
    views.setTextViewText(R.id.appwidget_race, raceLocation)
    views.setTextViewText(R.id.appwidget_time, raceTime)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}