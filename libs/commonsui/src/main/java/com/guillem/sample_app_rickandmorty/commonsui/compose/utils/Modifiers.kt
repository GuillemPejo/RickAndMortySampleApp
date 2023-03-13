package com.guillem.sample_app_rickandmorty.commonsui.compose.utils

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.debugBorder(color: Color = Color.Magenta) = border(1.dp, color)

fun Modifier.clickableIfNotNull(onClick: (() -> Unit)?) =
    onClick?.let { action ->
        clickable { action() }
    } ?: this

fun Modifier.modifyIf(condition: Boolean, modify: Modifier.() -> Modifier) =
    if (condition) then(modify()) else this
