package com.guillem.sample_app_rickandmorty.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.guillem.sample_app_rickandmorty.commonsui.navigation.scopedComposable
import com.guillem.sample_app_rickandmorty.ui.detail.DetailScreenDestination
import com.guillem.sample_app_rickandmorty.ui.home.HomeScreenDestination

object RootNavGraph {
    val StartDestination = HomeScreenDestination
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.rootNavGraph() {
    scopedComposable(HomeScreenDestination)
    scopedComposable(DetailScreenDestination)
}
