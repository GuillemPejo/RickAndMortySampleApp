package com.guillem.sample_app_rickandmorty.commonsui.compose.theme.providers

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import com.guillem.sample_app_rickandmorty.commonsui.compose.theme.ThemeMode

val LocalUiTheme = staticCompositionLocalOf<ThemeMode> { error("Not implemented") }

@Composable
@ReadOnlyComposable
fun ThemeMode.isDarkTheme(): Boolean =
    this == ThemeMode.Dark || (this == ThemeMode.FollowSystem && isSystemInDarkTheme())
