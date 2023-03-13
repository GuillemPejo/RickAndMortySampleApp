package com.guillem.sample_app_rickandmorty.commonsui.compose.utils

import android.annotation.SuppressLint
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.guillem.sample_app_rickandmorty.commonsui.compose.OnSystemBackPressed
import com.guillem.sample_app_rickandmorty.commonsui.compose.controllers.ViewController

@SuppressLint("ComposableNaming")
@Suppress("ComposableNaming")
@Composable
fun handleSystemBack(viewController: ViewController) {
    (viewController as? OnSystemBackPressed)?.let { listener ->
        MyBackHandler(onBack = { listener.onBackHandled() })
    }
}

@Composable
private fun MyBackHandler(onBack: () -> Boolean) {
    val currentOnBack by rememberUpdatedState(onBack)
    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
    }.onBackPressedDispatcher

    var backCallback: OnBackPressedCallback? = null
    backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val isSystemBackManaged = currentOnBack()
                backCallback?.isEnabled = isSystemBackManaged

                if (!isSystemBackManaged) {
                    backDispatcher.onBackPressed()
                }
            }
        }
    }

    SideEffect {
        backCallback.isEnabled = true
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, backDispatcher) {
        backDispatcher.addCallback(lifecycleOwner, backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}
