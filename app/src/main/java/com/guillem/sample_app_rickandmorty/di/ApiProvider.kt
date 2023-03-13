package com.guillem.sample_app_rickandmorty.di

import com.guillem.sample_app_rickandmorty.core.BuildConfig
import com.guillem.sample_app_rickandmorty.data.remote.ApiEndpoints
import com.guillem.sample_app_rickandmorty.data.remote.connectivity.NetworkConnectivityInterceptor
import com.guillem.sample_app_rickandmorty.data.remote.utils.addJsonConverterFactory
import com.guillem.sample_app_rickandmorty.data.remote.utils.addTimeout
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object ApiProvider {
    const val API_NAME = "network_api"

    fun provideApi(retrofit: Retrofit): ApiEndpoints = retrofit.create(ApiEndpoints::class.java)

    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addJsonConverterFactory()
        .build()

    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        networkConnectivityInterceptor: NetworkConnectivityInterceptor,

        ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(networkConnectivityInterceptor)
            .addInterceptor(loggingInterceptor)
            .addTimeout()
            .build()

    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }
}
