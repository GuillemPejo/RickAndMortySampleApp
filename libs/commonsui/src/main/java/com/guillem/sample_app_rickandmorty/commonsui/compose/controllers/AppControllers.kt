package com.guillem.sample_app_rickandmorty.commonsui.compose.controllers

import com.guillem.sample_app_rickandmorty.commonsui.navigation.Navigator
import com.guillem.sample_app_rickandmorty.core.app.FrontCoroutinesParams
import kotlinx.coroutines.CoroutineScope

data class AppControllers(
    val appScope: CoroutineScope,
    val coroutinesParams: FrontCoroutinesParams,
    val navigator: Navigator,
)
