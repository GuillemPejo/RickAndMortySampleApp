package com.guillem.sample_app_rickandmorty.domain.usecase

import androidx.paging.PagingData
import com.guillem.sample_app_rickandmorty.domain.model.Character
import com.guillem.sample_app_rickandmorty.domain.repository.CharactersRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetCharactersUseCaseImplTest{

    private val repository = mockk<CharactersRepository>()
    private val useCase = GetCharactersUseCaseImpl(repository)

    @Test
    fun `invoke should return a Flow of PagingData`() = runTest {
        val params = "Rick"
        val pagingData = mockk<Flow<PagingData<Character>>>()
        every { repository.getCharacters(params) } returns pagingData

        val result = useCase(params)

        verify(exactly = 1) { repository.getCharacters(params) }
        assertEquals(pagingData, result)
    }
}