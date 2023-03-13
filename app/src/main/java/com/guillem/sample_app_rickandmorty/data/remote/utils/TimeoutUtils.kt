package com.guillem.sample_app_rickandmorty.data.remote.utils

import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient

private const val REQUEST_TIME_OUT = 30L

internal fun OkHttpClient.Builder.addTimeout() =
    callTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
