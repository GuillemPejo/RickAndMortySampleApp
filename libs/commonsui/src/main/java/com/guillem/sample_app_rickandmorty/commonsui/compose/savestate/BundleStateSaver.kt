package com.guillem.sample_app_rickandmorty.commonsui.compose.savestate

import android.os.Bundle
import android.os.Parcelable
import androidx.compose.runtime.saveable.Saver
import java.io.Serializable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

private const val TypeUnsupported = "Type not supported. Provide support or change the type"

class BundleStateSaver(val bundle: Bundle) : StateSaver {
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

    override fun <T> setValue(key: String, value: T): Unit = when (value) {
        is Int -> bundle.putInt(key, value)
        is String -> bundle.putString(key, value)
        is Long -> bundle.putLong(key, value)
        is Float -> bundle.putFloat(key, value)
        is Parcelable -> bundle.putParcelable(key, value)
        is Serializable -> bundle.putSerializable(key, value)
        // TODO support more types
        else -> throw IllegalArgumentException(TypeUnsupported)
    }

    @Suppress("SwallowedException")
    override fun <T> getValue(key: String): T? = try {
        bundle.get(key) as T?
    } catch (e: ClassCastException) {
        throw IllegalArgumentException(TypeUnsupported)
    }

    override fun <T> getValue(key: String, default: T): T = getValue(key) ?: default

    override fun <T> setNullableValue(key: String, value: T?) {
        setValue(key, value.toNullableWrapper())
    }

    override fun <T> getNullableValue(key: String): T? =
        (bundle.getParcelable<NullableWrapper<T>>(key) as NullableWrapper<T>).value

    override fun <T> getNullableValue(key: String, default: T?): T? =
        if (bundle.containsKey(key)) getNullableValue(key) else default

    override fun unTrackKey(key: String) {
        trackedValues.remove(key)?.cancel()
    }

    companion object {
        val saver = Saver<BundleStateSaver, Bundle>(
            save = { it.bundle },
            restore = { BundleStateSaver(it) }
        )
    }
}

@Parcelize
data class NullableWrapper<T>(
    val value: @RawValue T?,
) : Parcelable

private fun <T> T?.toNullableWrapper() = NullableWrapper(this)
