package com.guillem.sample_app_rickandmorty.ui.detail

import com.guillem.sample_app_rickandmorty.commonsui.compose.controllers.AppControllers
import com.guillem.sample_app_rickandmorty.commonsui.compose.controllers.ViewController
import com.guillem.sample_app_rickandmorty.ui.model.CharacterUI
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Suppress("UnusedPrivateMember")
class DetailScreenViewModel(
    private val app: AppControllers,
    characterEncoded: String?,
    ) : ViewController(app.coroutinesParams) {
    private val _characterData = MutableStateFlow<CharacterUI?>(null)
    val characterData = _characterData.asStateFlow()

    init {
        characterEncoded?.let {
            _characterData.value = Json.decodeFromString<CharacterUI>(it)
        }
    }

    fun onBackButtonClicked() {
        app.navigator.navUp()
    }
}

