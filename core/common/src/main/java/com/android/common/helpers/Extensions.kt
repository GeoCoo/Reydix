package com.android.common.helpers

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun <T> Flow<T>.safeAsync(with: (Throwable) -> (T)): Flow<T> {
    return this.flowOn(Dispatchers.IO).catch { emit(with(it)) }
}

fun Context.readAssetFile(fileName: String): String =
    assets.open(fileName).bufferedReader().use { it.readText() }

inline fun <reified T> T.serialize(gson: Gson): String =
    URLEncoder.encode(gson.toJson(this), StandardCharsets.UTF_8.toString())

inline fun <reified T> String?.deserializeList(gson: Gson): T? {
    val type = object : TypeToken<T>() {}.type
    return if (this.isNullOrEmpty().not()) gson.fromJson(this, type) else null
}


