package com.guillem.sample_app_rickandmorty.commonsui.compose.savestate

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HandleStateSaver(private val savedStateHandle: SavedStateHandle) : StateSaver {
    private val trackedValues = mutableMapOf<String, Job>()

    override fun <T> getAutoSaveFlow(
        scope: CoroutineScope,
        key: String,
        default: T
    ): MutableStateFlow<T> {
        val flow = MutableStateFlow(getValue(key, default))
        val job = scope.launch {
            flow.collect { setValue(key, it) }
        }
        unTrackKey(key)
        trackedValues[key] = job
        return flow
    }

    override fun <T> getNullableAutoSaveFlow(
        scope: CoroutineScope,
        key: String,
        default: T?
    ): MutableStateFlow<T?> {
        val flow = MutableStateFlow(getNullableValue(key, default))
        val job = scope.launch {
            flow.collect { setNullableValue(key, it) }
        }
        unTrackKey(key)
        trackedValues[key] = job
        return flow
    }

    override fun <T> setValue(key: String, value: T): Unit = savedStateHandle.set(key, value)

    override fun <T> getValue(key: String): T? = savedStateHandle.get<T>(key)
    override fun <T> getValue(key: String, default: T): T = getValue(key) ?: default

    override fun <T> setNullableValue(key: String, value: T?) = savedStateHandle.set(key, value)
    override fun <T> getNullableValue(key: String): T? = getValue(key)
    override fun <T> getNullableValue(key: String, default: T?): T? =
        if (key in savedStateHandle) getNullableValue(key) else default

    override fun unTrackKey(key: String) {
        trackedValues.remove(key)?.cancel()
    }
}

fun SavedStateHandle.buildSaver(): StateSaver = HandleStateSaver(this)
