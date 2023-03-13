package com.guillem.sample_app_rickandmorty.commonsui.compose.theme.providers

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import com.guillem.sample_app_rickandmorty.commonsui.navigation.transition.MotionTransition

@Immutable
data class MotionDuration(
    val durationShort1: Long,
    val durationShort2: Long,
    val durationMedium1: Long,
    val durationMedium2: Long,
    val durationLong1: Long,
    val durationLong2: Long,
)

val LocalMotionDuration = staticCompositionLocalOf {
    defaultMotionDuration
}

val LocalMotionTransition = staticCompositionLocalOf {
    defaultMotionTransition
}

internal val defaultMotionDuration = MotionDuration(
    durationShort1 = 100,
    durationShort2 = 200,
    durationMedium1 = 300,
    durationMedium2 = 400,
    durationLong1 = 500,
    durationLong2 = 600,
)

internal val defaultMotionTransition = MotionTransition(
    transitionSlide = 50,
    transitionInitialScale = 0.8F,
    transitionTargetScale = 1.1F,
    transitionDuration = defaultMotionDuration.durationMedium1,
)

internal const val TRANSITION_SLIDE_DPS = 30F
