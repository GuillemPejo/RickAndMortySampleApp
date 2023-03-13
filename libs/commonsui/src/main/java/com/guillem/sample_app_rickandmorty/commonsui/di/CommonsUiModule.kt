package com.guillem.sample_app_rickandmorty.commonsui.di

import com.guillem.sample_app_rickandmorty.commonsui.compose.controllers.AppControllers
import com.guillem.sample_app_rickandmorty.commonsui.navigation.Navigator
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val commonsUiModule = module {
    single { Navigator() }
    factoryOf(::AppControllers)
}
