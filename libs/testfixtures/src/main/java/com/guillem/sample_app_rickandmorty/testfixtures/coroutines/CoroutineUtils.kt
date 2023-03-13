package com.guillem.sample_app_rickandmorty.testfixtures.coroutines

import com.guillem.sample_app_rickandmorty.core.app.FrontCoroutinesParams
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler

fun createCoroutineExceptionHandler() = CoroutineExceptionHandler { _, throwable ->
    throw throwable
}

fun createCoroutineParams(testDispatcher: CoroutineDispatcher) = FrontCoroutinesParams(
    TestDispatchers(testDispatcher),
    createCoroutineExceptionHandler(),
)
