package com.guillem.sample_app_rickandmorty.commonsui.compose.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.guillem.sample_app_rickandmorty.commonsui.compose.theme.providers.AppTheme
import com.guillem.sample_app_rickandmorty.commonsui.compose.theme.providers.ThemeColors
import com.guillem.sample_app_rickandmorty.commonsui.compose.theme.providers.buildDark
import com.guillem.sample_app_rickandmorty.commonsui.compose.theme.providers.buildLight
import com.guillem.sample_app_rickandmorty.commonsui.compose.theme.providers.isDarkTheme
import com.guillem.sample_app_rickandmorty.commonsui.compose.theme.providers.toMaterialColors

private val LightColorPalette = ThemeColors.buildLight(
    greenDot = greenDot,
    redDot = redDot,
    unknownDot = unknownDot,
    defaultTextColor = softBlack,
)

private val DarkColorPalette = ThemeColors.buildDark(
    greenDot = greenDot,
    redDot = redDot,
    unknownDot = unknownDot,
    defaultTextColor = white,
)

enum class ThemeMode {
    FollowSystem,
    Light,
    Dark,
}

@Composable
fun RickAndMortyTheme(
    themeMode: ThemeMode = ThemeMode.FollowSystem,
    content: @Composable () -> Unit,
) {
    val isDarkTheme = themeMode.isDarkTheme()
    val colorScheme = if (isDarkTheme) DarkColorPalette else LightColorPalette

    if (!isLoadedFromPreview()) {
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(
            color = colorScheme.surface,
            darkIcons = !isDarkTheme,
        )
    }

    AppTheme(colorScheme, themeMode) {
        MaterialTheme(
            colorScheme = colorScheme.toMaterialColors(),
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}

@Composable
private fun isLoadedFromPreview() = LocalInspectionMode.current
