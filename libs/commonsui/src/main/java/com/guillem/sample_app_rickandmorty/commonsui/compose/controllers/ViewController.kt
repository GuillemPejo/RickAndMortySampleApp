package com.guillem.sample_app_rickandmorty.commonsui.compose.controllers

import com.guillem.sample_app_rickandmorty.core.app.FrontCoroutinesParams
import com.guillem.sample_app_rickandmorty.core.utils.Cancellable
import com.guillem.sample_app_rickandmorty.core.utils.mapState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow

/**
 * Class to be used for UI presenters and viewModels.
 * Clear will be automatically called if the ViewController is declared in a Koin scope bound to
 * navigation. It should be declared as follows:
 * ```
 * scope<T> {
 *     scoped { MyViewController() } bind Cancellable::class
 * }
 * ```
 */
open class ViewController(
    coroutinesParams: FrontCoroutinesParams,
    scope: CoroutineScope? = null,
) : Cancellable {
    protected val scope = coroutinesParams.createScope(scope)

    override fun cancel() {
        scope.cancel()
    }

    fun <T, K> StateFlow<T>.mapState(
        started: SharingStarted = SharingStarted.WhileSubscribed(),
        transform: (data: T) -> K,
    ): StateFlow<K> = mapState(scope = scope, transform = transform, started = started)
}
