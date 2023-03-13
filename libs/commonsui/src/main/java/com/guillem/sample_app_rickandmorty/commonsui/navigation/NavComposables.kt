package com.guillem.sample_app_rickandmorty.commonsui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.guillem.sample_app_rickandmorty.commonsui.compose.CancellableScopeCallback
import com.guillem.sample_app_rickandmorty.commonsui.compose.ScopeLifecycleHandler
import com.google.accompanist.navigation.animation.composable
import org.koin.androidx.compose.getKoin
import org.koin.core.scope.Scope

@OptIn(ExperimentalAnimationApi::class)
private typealias AnimatedEnterTransition =
    (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)?

@OptIn(ExperimentalAnimationApi::class)
private typealias AnimatedExitTransition =
    (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)?

/**
 * Substitute for navigation
 * This helper function can be used for declaring nested navigation graphs when we want to have
 * a scope bound to that nested graph.
 * It is only for convenience, as it provides the NestedNavGraph that can be used by the composables
 * in that graph.
 */
inline fun <T : NavEntry> NavGraphBuilder.scopedNavigation(
    nestedNavGraph: SubgraphDeclaration<T>,
    crossinline builder: NavGraphBuilder.(SubgraphDeclaration<T>) -> Unit
): Unit = navigation(nestedNavGraph.startDestination.route, nestedNavGraph.route) {
    builder(nestedNavGraph)
}

/**
 * Substitute for composable
 * This helper function is used to declare any screen for which we want to have a scope.
 */
@ExperimentalAnimationApi
@Suppress("LongParameterList")
inline fun <OuterArgs, reified Dest : Destination<OuterArgs>, Dec : DestinationDeclaration<Dest>>
NavGraphBuilder.scopedComposable(
    declaration: Dec,
    outerArgs: OuterArgs,
    noinline enterTransition: AnimatedEnterTransition = null,
    noinline exitTransition: AnimatedExitTransition = null,
    noinline popEnterTransition: AnimatedEnterTransition = null,
    noinline popExitTransition: AnimatedExitTransition = null,
) {
    composable(
        route = declaration.route,
        arguments = declaration.arguments,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
    ) { navEntry ->
        val args = navEntry.arguments
        val destination = remember(args) { declaration.from(args) }
        val destinationIdentifier = remember(destination) { destination.buildIdentifier() }
        val koinScope = getKoin().getOrCreateScope<Dest>(destinationIdentifier)
        koinScope.registerCallback(CancellableScopeCallback())

        RunOnce {
            ScopeLifecycleHandler().bind(koinScope, navEntry.lifecycle)
        }

        destination.Content(navEntry = navEntry, diScope = koinScope, outerArgs = outerArgs)
    }
}

/**
 * scopedComposable for simpler use when there are no outerArgs
 */
@ExperimentalAnimationApi
inline fun <reified Dest : Destination<Unit>, Dec : DestinationDeclaration<Dest>>
NavGraphBuilder.scopedComposable(
    declaration: Dec,
    noinline enterTransition: AnimatedEnterTransition = null,
    noinline exitTransition: AnimatedExitTransition = null,
    noinline popEnterTransition: AnimatedEnterTransition = null,
    noinline popExitTransition: AnimatedExitTransition = null,
) {
    scopedComposable(
        declaration = declaration,
        outerArgs = Unit,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
    )
}

/**
 * Substitute for composable in nested graphs
 * This helper function is used to declare screens in nested graphs, when we want the scope of
 * the nested graph and the scope of this screen.
 */
@ExperimentalAnimationApi
@Suppress("LongParameterList")
inline fun <OuterArgs, reified Dest : Destination<OuterArgs>, Dec : DestinationDeclaration<Dest>,
    reified Graph : Subgraph> NavGraphBuilder.doubleScopedComposable(
    declaration: Dec,
    parentGraph: SubgraphDeclaration<Graph>,
    navController: NavController,
    crossinline outerArgsGetter: (NavBackStackEntry, parentScope: Scope) -> OuterArgs,
    noinline enterTransition: AnimatedEnterTransition = null,
    noinline exitTransition: AnimatedExitTransition = null,
    noinline popEnterTransition: AnimatedEnterTransition = null,
    noinline popExitTransition: AnimatedExitTransition = null,
) {
    composable(
        route = declaration.route,
        arguments = declaration.arguments,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
    ) { navEntry ->
        val args = navEntry.arguments
        val graphDestination = remember(args) { parentGraph.from(args) }
        val graphDestinationIdentifier = remember(graphDestination) {
            graphDestination.buildIdentifier()
        }
        val parentScope = getKoin().getOrCreateScope<Graph>(graphDestinationIdentifier)

        val destination = remember(args) { declaration.from(args) }
        val destinationIdentifier = remember(destination) { destination.buildIdentifier() }
        val koinScope = getKoin().getOrCreateScope<Dest>(destinationIdentifier)

        RunOnce {
            val parentEntry = navEntry.getParentEntry(navController)
            if (parentEntry != null) {
                parentScope.getOrNull<ScopeLifecycleHandler>()?.bind(
                    scope = parentScope,
                    lifecycle = parentEntry.lifecycle,
                )
            }

            ScopeLifecycleHandler().bind(koinScope, navEntry.lifecycle)
        }

        destination.Content(
            navEntry = navEntry,
            diScope = koinScope,
            outerArgs = outerArgsGetter(navEntry, parentScope),
        )
    }
}

fun NavBackStackEntry.getParentEntry(navController: NavController): NavBackStackEntry? =
    destination.parent?.id?.let { parentId ->
        navController.backQueue.lastOrNull { entry ->
            entry.destination.id == parentId
        }
    }

fun NavBackStackEntry.getParentOrThis(navController: NavController): NavBackStackEntry =
    getParentEntry(navController) ?: this

/**
 * Hack so that an effect can be run only once and not on each composition.
 */
@Composable
fun RunOnce(composable: () -> Unit) {
    rememberSaveable {
        composable()
        true
    }
}
