package com.guillem.sample_app_rickandmorty.core.di

import android.annotation.SuppressLint
import com.guillem.sample_app_rickandmorty.core.BuildConfig
import com.guillem.sample_app_rickandmorty.core.app.AppDispatchers
import com.guillem.sample_app_rickandmorty.core.app.BackDispatchers
import com.guillem.sample_app_rickandmorty.core.app.DomainDispatcher
import com.guillem.sample_app_rickandmorty.core.app.FrontCoroutinesParams
import com.guillem.sample_app_rickandmorty.core.app.FrontDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.plus
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.binds
import org.koin.dsl.module
import timber.log.Timber

@SuppressLint("DispatchersDetector")
val coreModule = module {
    single(named(DiConstants.Dispatchers.UI)) { Dispatchers.Main.immediate as CoroutineDispatcher }
    single(named(DiConstants.Dispatchers.CPU)) { Dispatchers.Default }
    single(named(DiConstants.Dispatchers.IO)) { Dispatchers.IO }
    single {
        AppDispatchers(
            get(named(DiConstants.Dispatchers.UI)),
            get(named(DiConstants.Dispatchers.CPU)),
            get(named(DiConstants.Dispatchers.IO)),
        )
    } binds(arrayOf(DomainDispatcher::class, FrontDispatchers::class, BackDispatchers::class))

    single { getCoroutineExceptionHandler() }
    single { CoroutineScope(SupervisorJob()) + get<CoroutineExceptionHandler>() }
    factoryOf(::FrontCoroutinesParams)
}

private fun getCoroutineExceptionHandler(
) = CoroutineExceptionHandler { _, exception ->
    Timber.d("CoroutineExceptionHandler captured exception: $exception")
    exception.printStackTrace()
    if (BuildConfig.DEBUG) throw exception else Timber.e(exception)
}
