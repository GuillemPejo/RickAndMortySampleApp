package com.guillem.sample_app_rickandmorty.data.remote.utils

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

private val json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
    encodeDefaults = true
}

internal fun Retrofit.Builder.addJsonConverterFactory() =
    addConverterFactory(json.asConverterFactory(getJsonMediaType()))

internal fun getJsonMediaType() = "application/json".toMediaType()
