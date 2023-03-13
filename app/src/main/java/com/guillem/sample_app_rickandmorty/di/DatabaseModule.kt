package com.guillem.sample_app_rickandmorty.di

import com.guillem.sample_app_rickandmorty.data.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    factory { AppDatabase.getInstance(androidContext()).keysDao() }
    factory { AppDatabase.getInstance(androidContext()).charactersDao() }
    factory { AppDatabase.getInstance(androidContext()) }
}
