package com.guillem.sample_app_rickandmorty.di

import com.guillem.sample_app_rickandmorty.data.remote.connectivity.NetworkConnectivityInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {

    single {
        ApiProvider.provideApi(get(named(ApiProvider.API_NAME)))
    }

    single(named(ApiProvider.API_NAME)) {
        ApiProvider.provideRetrofit(get(named(ApiProvider.API_NAME)))
    }

    single(named(ApiProvider.API_NAME)) {
        ApiProvider.provideOkHttpClient(
            networkConnectivityInterceptor = get(),
            loggingInterceptor = get(),
        )
    }

    single { ApiProvider.provideLoggingInterceptor() }

    singleOf(::NetworkConnectivityInterceptor)

}
