package com.github.alexverdonck.raceschedule

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.github.alexverdonck.raceschedule.data.Events
import com.github.alexverdonck.raceschedule.data.next
import com.github.alexverdonck.raceschedule.data.raceSession
import com.github.alexverdonck.raceschedule.utils.readJsonFromRaw

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
        Events.events.value = readJsonFromRaw(context, R.raw.events)
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
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