package com.guillem.sample_app_rickandmorty.core.app

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.plus

data class AppDispatchers(
    override val ui: CoroutineDispatcher,
    override val cpu: CoroutineDispatcher,
    override val io: CoroutineDispatcher,
) : DomainDispatcher, FrontDispatchers, BackDispatchers

interface FrontDispatchers {
    val ui: CoroutineDispatcher
    val cpu: CoroutineDispatcher
}

interface BackDispatchers {
    val cpu: CoroutineDispatcher
    val io: CoroutineDispatcher
}

interface DomainDispatcher {
    val cpu: CoroutineDispatcher
}

data class FrontCoroutinesParams(
    val dispatchers: FrontDispatchers,
    val exceptionHandler: CoroutineExceptionHandler,
) {
    fun createScope(
        scope: CoroutineScope? = null,
        dispatcher: CoroutineDispatcher = dispatchers.ui,
    ) = if (scope != null) {
        scope + dispatcher + exceptionHandler
    } else {
        CoroutineScope(SupervisorJob() + dispatcher) + exceptionHandler
    }
}
