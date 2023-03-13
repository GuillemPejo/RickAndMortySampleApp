package com.guillem.sample_app_rickandmorty

import android.app.Application
import com.guillem.sample_app_rickandmorty.commonsui.di.commonsUiModule
import com.guillem.sample_app_rickandmorty.core.di.coreModule
import com.guillem.sample_app_rickandmorty.di.appModule
import com.guillem.sample_app_rickandmorty.di.databaseModule
import com.guillem.sample_app_rickandmorty.di.networkModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
class BaseApplication : Application() {
    private val applicationScope: CoroutineScope by inject()

    override fun onCreate() {
        super.onCreate()
        setupDi()
    }

    private fun setupDi() {
        startKoin {
            androidContext(this@BaseApplication)
            appModule.single { applicationScope }
            modules(
                listOf(
                    appModule,
                    coreModule,
                    commonsUiModule,
                    networkModule,
                    databaseModule,
                )
            )
        }
    }
}
