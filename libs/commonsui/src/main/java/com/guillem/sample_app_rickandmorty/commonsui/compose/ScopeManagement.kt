package com.guillem.sample_app_rickandmorty.commonsui.compose

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.guillem.sample_app_rickandmorty.core.utils.Cancellable
import org.koin.core.scope.Scope
import org.koin.core.scope.ScopeCallback

class ScopeLifecycleHandler {
    private var boundToLifecycle = false

    fun bind(scope: Scope, lifecycle: Lifecycle) {
        if (!boundToLifecycle) {
            boundToLifecycle = true
            scope.registerCallback(CancellableScopeCallback())

            lifecycle.addOnStateChangeListener { _, event ->
                if (event == Lifecycle.Event.ON_DESTROY) {
                    scope.close()
                }
            }
        }
    }
}

class CancellableScopeCallback : ScopeCallback {
    override fun onScopeClose(scope: Scope) {
        scope.getAll<Cancellable>().forEach { it.cancel() }
    }
}

private fun Lifecycle.addOnStateChangeListener(
    onStateChanged: (LifecycleOwner, Lifecycle.Event) -> Unit,
) {
    addObserver(object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            onStateChanged(source, event)
        }
    })
}
