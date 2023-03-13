package com.guillem.sample_app_rickandmorty.data.remote.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class NetworkConnectivityInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) throw NoConnectivityException()

        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    private fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val networkCapabilities =
            connectivityManager?.getNetworkCapabilities(connectivityManager.activeNetwork)
        return (
            networkCapabilities?.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            ) == true || networkCapabilities?.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI
            ) == true
            )
    }
}

class NoConnectivityException : IOException() {
    override val message: String = "No internet connection"
}
