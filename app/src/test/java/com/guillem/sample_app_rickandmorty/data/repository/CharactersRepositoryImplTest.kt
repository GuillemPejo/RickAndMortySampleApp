package com.guillem.sample_app_rickandmorty.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.guillem.sample_app_rickandmorty.data.local.AppDatabase
import com.guillem.sample_app_rickandmorty.data.local.entity.CharacterEntity
import com.guillem.sample_app_rickandmorty.data.remote.ApiEndpoints
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CharactersRepositoryImplTest{
    private lateinit var api: ApiEndpoints
    private lateinit var db: AppDatabase
    private lateinit var repository: CharactersRepositoryImpl

    @Before
    fun setup() {
        api = mockk()
        db = mockk()
        repository = CharactersRepositoryImpl(api, db)
    }

    @Test
    fun `getCharacters returns expected results`() = runTest {
        val characterName = "Rick"
        val pagingData: PagingData<CharacterEntity> = mockk()
        every { db.charactersDao().getCharactersByName(characterName) }
        every { pagingData  } returns pagingData

        val result = repository.getCharacters(characterName).toList()

        verify { db.charactersDao().getCharactersByName(characterName) }
        assertEquals(listOf(pagingData.map { it.toDomain() }), result)
    }
}