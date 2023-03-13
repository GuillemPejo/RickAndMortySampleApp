package com.guillem.sample_app_rickandmorty.commonsui.navigation

import com.guillem.sample_app_rickandmorty.commonsui.compose.ScopeLifecycleHandler
import com.guillem.sample_app_rickandmorty.core.utils.Cancellable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.TypeQualifier
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.ScopeDSL
import org.koin.dsl.bind

/**
 * Wrapper over Koin scopes for nested navigation graphs. It handles the lifecycle.
 */
inline fun <reified T> Module.navGraphScope(crossinline scopeSet: ScopeDSL.() -> Unit) {
    val qualifier = TypeQualifier(T::class)
    scope(qualifier) {
        scoped { ScopeLifecycleHandler() }
        scopeSet()
    }
}

/**
 * Koin getter to avoid repetition of parametersOf lambda
 */
inline fun <reified T : Any> Scope.getWith(vararg parameters: Any?): T {
    return get(T::class, null) { parametersOf(*parameters) }
}

/**
 * Utility to add a coroutine scope to a Koin scope. It will be automatically cancelled if the
 * scope is used with navigation utils.
 */
fun ScopeDSL.attachScopedCoroutine() {
    val coroutineScope = CoroutineScope(SupervisorJob())
    scoped { CancellableCoroutineScope(coroutineScope) } bind Cancellable::class
    scoped(named(Di.ScopedCoroutine)) { coroutineScope }
}

fun Scope.getScopedCoroutineScopeOrNull(): CoroutineScope? =
    getOrNull<CoroutineScope>(named(Di.ScopedCoroutine))
fun Scope.getScopedCoroutineScope(): CoroutineScope = get<CoroutineScope>(named(Di.ScopedCoroutine))

private class CancellableCoroutineScope(private val coroutineScope: CoroutineScope) : Cancellable {
    override fun cancel() {
        coroutineScope.cancel()
    }
}

object Di {
    const val ScopedCoroutine = "ScopedCoroutine"
}
