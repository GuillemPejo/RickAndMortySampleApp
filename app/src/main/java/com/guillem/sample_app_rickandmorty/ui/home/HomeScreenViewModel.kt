package com.guillem.sample_app_rickandmorty.ui.home

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.guillem.sample_app_rickandmorty.commonsui.compose.controllers.AppControllers
import com.guillem.sample_app_rickandmorty.commonsui.compose.controllers.ViewController
import com.guillem.sample_app_rickandmorty.commonsui.navigation.transition.NavTransition
import com.guillem.sample_app_rickandmorty.domain.usecase.GetCharactersUseCase
import com.guillem.sample_app_rickandmorty.ui.detail.DetailScreenDestination
import com.guillem.sample_app_rickandmorty.ui.model.CharacterUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Suppress("UnusedPrivateMember")
class HomeScreenViewModel(
    private val app: AppControllers,
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewController(app.coroutinesParams) {

    private val _characters = MutableStateFlow<PagingData<CharacterUI>>(PagingData.empty())
    val characters: StateFlow<PagingData<CharacterUI>> = _characters.asStateFlow()
    val searchTextFieldText = MutableStateFlow("")
    private val _searchText = MutableStateFlow<String?>(null)
    val searchText = _searchText.asStateFlow()


    init {
        scope.launch {
            _searchText.collectLatest {
                getCharactersByName(it.orEmpty())
            }
        }
    }

    fun onItemClick(item: CharacterUI) {
        val itemEncoded = Json.encodeToString(item)
        app.navigator.goTo(DetailScreenDestination(itemEncoded), transition = NavTransition.SharedAxisX)
    }

    fun onSearchButtonClicked() {
        _searchText.value = searchTextFieldText.value
    }

    private fun getCharactersByName(characterName: String) {
        scope.launch {
            getCharactersUseCase(characterName)
                .cachedIn(scope)
                .collect {
                    _characters.value = it.map { character -> character.toUi() }
                }
        }
    }
}

