package com.guillem.sample_app_rickandmorty.ui.detail

import com.guillem.sample_app_rickandmorty.commonsui.compose.controllers.AppControllers
import com.guillem.sample_app_rickandmorty.fake.getMockCharacterEncodedJson
import com.guillem.sample_app_rickandmorty.fake.getMockCharacterUI
import com.guillem.sample_app_rickandmorty.testfixtures.buildAppController
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.verify
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DetailScreenViewModelTest{
    private val dispatcher = StandardTestDispatcher()
    private lateinit var appControllers: AppControllers

    @Before
    fun setup(){
        appControllers = buildAppController(
            dispatcher = dispatcher,
        )
    }

    @Test
    fun `characterData should be updated with decoded value`() {
        val characterJson = getMockCharacterEncodedJson()
        val characterUI = getMockCharacterUI()
        val viewModel = DetailScreenViewModel(appControllers, characterJson)

        assertEquals(characterUI, viewModel.characterData.value)
    }

    @Test
    fun `onBackButtonClicked should navigate up`() {

        every { appControllers.navigator.navUp() } just Runs

        val viewModel = DetailScreenViewModel(appControllers, null)

        viewModel.onBackButtonClicked()

        verify { appControllers.navigator.navUp() }
    }
}