package com.guillem.sample_app_rickandmorty.commonsui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Suppress("StateFlowValueCalledInComposition")
@Composable
fun <T : R, R> StateFlow<T>.collectWithLifecycle(
    context: CoroutineContext = EmptyCoroutineContext,
): State<R> = collectWithLifecycle(initial = value, context = context)

@Composable
fun <T : R, R> Flow<T>.collectWithLifecycle(
    initial: R,
    context: CoroutineContext = EmptyCoroutineContext,
): State<R> {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleAwareFlow = remember(key1 = this, key2 = lifecycleOwner) {
        this.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }
    return lifecycleAwareFlow.collectAsState(initial = initial, context = context)
}
