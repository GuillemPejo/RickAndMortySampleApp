package com.guillem.sample_app_rickandmorty.commonsui.navigation

import com.guillem.sample_app_rickandmorty.commonsui.navigation.transition.NavTransition
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

open class Navigator {

    private val _directions = MutableSharedFlow<Route>(extraBufferCapacity = 1)
    val directions: SharedFlow<Route> = _directions

    open fun goTo(
        destination: Destination<*>,
        transition: NavTransition = NavTransition.Default,
        popStrategy: NavPop = NavPop.Nothing,
        launchSingleTop: Boolean = false,
    ) {
        _directions.tryEmit(
            Route.Forward(destination.build(transition), popStrategy, launchSingleTop)
        )
    }

    /**
     * Navigation to be performed when the user presses the system back button.
     * **Not for in-app back arrow buttons**.
     */
    open fun systemBack() {
        _directions.tryEmit(Route.Back)
    }

    /**
     * Navigation to be performed when the user presses the in-app back button.
     * The behavior is not always the same as pressing the system back button.
     */
    open fun navUp() {
        _directions.tryEmit(Route.Up)
    }
}

sealed class NavPop {
    object Nothing : NavPop()
    object Start : NavPop()
    data class Inclusive(val upTo: DestinationDeclaration<*>) : NavPop()
    data class Exclusive(val upTo: DestinationDeclaration<*>) : NavPop()
}
