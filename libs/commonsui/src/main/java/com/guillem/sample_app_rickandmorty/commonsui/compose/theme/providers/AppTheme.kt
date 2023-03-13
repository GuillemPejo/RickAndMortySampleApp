package com.guillem.sample_app_rickandmorty.commonsui.compose.theme.providers

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.guillem.sample_app_rickandmorty.commonsui.compose.theme.ThemeMode
import com.guillem.sample_app_rickandmorty.commonsui.navigation.transition.MotionTransition

@Composable
fun AppTheme(
    themeColors: ThemeColors,
    themeMode: ThemeMode,
    content: @Composable () -> Unit,
) {
    val motionTransition = defaultMotionTransition.copy(
        transitionSlide = with(LocalDensity.current) { TRANSITION_SLIDE_DPS.dp.toPx().toInt() },
    )
    CompositionLocalProvider(
        LocalMotionDuration provides defaultMotionDuration,
        LocalMotionTransition provides motionTransition,
        LocalColors provides themeColors,
    ) {
        content()
    }
}

object AppTheme {
    val duration: MotionDuration
        @Composable
        get() = LocalMotionDuration.current

    val transition: MotionTransition
        @Composable
        get() = LocalMotionTransition.current

    val colors: ThemeColors
        @Composable
        get() = LocalColors.current

    val typography: Typography
        @Composable
        get() = MaterialTheme.typography

    val shapes: Shapes
        @Composable
        get() = MaterialTheme.shapes

    val uiTheme: ThemeMode
        @Composable
        get() = LocalUiTheme.current

    val appPadding = 16.dp
    val minTouchSize = 48.dp
}
