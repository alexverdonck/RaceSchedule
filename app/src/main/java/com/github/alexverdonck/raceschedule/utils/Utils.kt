package com.github.alexverdonck.raceschedule.utils

import android.content.Context
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T> readJsonFromRaw(context: Context, resource: Int): T {
    val res: InputStream = context.resources.openRawResource(resource)
    val inputStreamReader = InputStreamReader(res)
    val sb = StringBuilder()
    var line: String?
    val br = BufferedReader(inputStreamReader)
    line = br.readLine()
    while (line != null) {
        sb.append(line)
        line = br.readLine()
    }

    return Json.decodeFromString<T>(sb.toString())
}