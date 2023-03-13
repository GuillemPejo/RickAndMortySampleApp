package com.guillem.sample_app_rickandmorty.testfixtures

import com.guillem.sample_app_rickandmorty.commonsui.compose.controllers.AppControllers
import com.guillem.sample_app_rickandmorty.commonsui.navigation.Navigator
import com.guillem.sample_app_rickandmorty.testfixtures.coroutines.createCoroutineParams
import kotlinx.coroutines.CoroutineDispatcher

fun buildAppController(
    dispatcher: CoroutineDispatcher,
) = AppControllers(
    appScope = createCoroutineParams(dispatcher).createScope(),
    coroutinesParams = createCoroutineParams(dispatcher),
    navigator = Navigator(),
)

