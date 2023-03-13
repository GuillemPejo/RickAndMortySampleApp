package com.guillem.sample_app_rickandmorty.presentation

import androidx.compose.runtime.Composable

@Composable
fun AppLayout() {
    NavigationHost(startDestinationPath = RootNavGraph.StartDestination.route) {
        rootNavGraph()
    }
}
