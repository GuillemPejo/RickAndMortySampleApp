package com.guillem.sample_app_rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.guillem.sample_app_rickandmorty.commonsui.compose.theme.RickAndMortyTheme
import com.guillem.sample_app_rickandmorty.presentation.AppLayout

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            RickAndMortyTheme() {
                AppLayout()
            }
        }
    }
}
