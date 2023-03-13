package com.guillem.sample_app_rickandmorty.testfixtures.coroutines

import com.guillem.sample_app_rickandmorty.core.app.BackDispatchers
import com.guillem.sample_app_rickandmorty.core.app.DomainDispatcher
import com.guillem.sample_app_rickandmorty.core.app.FrontDispatchers
import kotlinx.coroutines.CoroutineDispatcher

class TestDispatchers(
    override val ui: CoroutineDispatcher,
    override val cpu: CoroutineDispatcher,
    override val io: CoroutineDispatcher,
) : DomainDispatcher, FrontDispatchers, BackDispatchers {
    constructor(dispatcher: CoroutineDispatcher) : this(dispatcher, dispatcher, dispatcher)
}
