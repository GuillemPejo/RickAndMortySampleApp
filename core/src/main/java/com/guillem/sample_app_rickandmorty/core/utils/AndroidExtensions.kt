package com.guillem.sample_app_rickandmorty.core.utils

import android.content.res.Configuration
import android.content.res.Resources

fun Resources.isNightModeActive() =
    (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
