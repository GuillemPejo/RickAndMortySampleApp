package com.guillem.sample_app_rickandmorty.ui.home

import androidx.paging.PagingData
import androidx.paging.map
import com.guillem.sample_app_rickandmorty.commonsui.compose.controllers.AppControllers
import com.guillem.sample_app_rickandmorty.commonsui.navigation.transition.NavTransition
import com.guillem.sample_app_rickandmorty.domain.repository.CharactersRepository
import com.guillem.sample_app_rickandmorty.domain.usecase.GetCharactersUseCase
import com.guillem.sample_app_rickandmorty.domain.usecase.GetCharactersUseCaseImpl
import com.guillem.sample_app_rickandmorty.fake.getMockCharacterEncodedJson
import com.guillem.sample_app_rickandmorty.fake.getMockCharacterList
import com.guillem.sample_app_rickandmorty.fake.getMockCharacterUI
import com.guillem.sample_app_rickandmorty.testfixtures.buildAppController
import com.guillem.sample_app_rickandmorty.ui.detail.DetailScreenDestination
import com.guillem.sample_app_rickandmorty.ui.model.CharacterUI
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test

class HomeScreenViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private val repository = mockk<CharactersRepository>()
    private val useCase = GetCharactersUseCaseImpl(repository)
    private val getCharactersUseCase: GetCharactersUseCase = mockk()

    private lateinit var appControllers: AppControllers
    private lateinit var viewModel: HomeScreenViewModel

    @Before
    fun setup() {
        appControllers = buildAppController(
            dispatcher = dispatcher,
        )
        viewModel = HomeScreenViewModel(appControllers, useCase)
    }


    @Test
    fun `onItemClick should navigate to detail screen`() {
        val characterUI = getMockCharacterUI()
        val itemEncoded = getMockCharacterEncodedJson()
        every { Json.encodeToString(characterUI) } returns itemEncoded

        viewModel.onItemClick(characterUI)

        verify {
            appControllers.navigator.goTo(
                DetailScreenDestination(itemEncoded),
                transition = NavTransition.SharedAxisX
            )
        }
    }

    @Test
    fun `onSearchButtonClicked should get characters by name`() = runTest {
        val characterName = "Garmanarnar"
        viewModel.searchTextFieldText.value = characterName
        val characters = PagingData.from(listOf(getMockCharacterList()[1]))
        coEvery { getCharactersUseCase(characterName) } returns flowOf(characters)

        viewModel.onSearchButtonClicked()

        val expectedCharacters = characters.map { it.toUi() }
        assertEquals(expectedCharacters, viewModel.characters.value)
    }

    @Test
    fun `onSearchButtonClicked should handle empty character name`() = runTest {
        viewModel.searchTextFieldText.value = "mockRandomText"
        coEvery { getCharactersUseCase("mockRandomText") } returns flowOf()

        viewModel.onSearchButtonClicked()

        val expectedCharacters = PagingData.empty<CharacterUI>()
        assertEquals(expectedCharacters, viewModel.characters.value)
    }

    @Test
    fun `onSearchButtonClicked should update search text`() {
        val searchText = "mockSearchText"
        viewModel.searchTextFieldText.value = searchText
        viewModel.onSearchButtonClicked()

        assert(viewModel.searchText.value == searchText)
    }
}