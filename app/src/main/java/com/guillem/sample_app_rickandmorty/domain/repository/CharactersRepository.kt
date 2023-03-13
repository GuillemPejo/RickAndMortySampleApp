package com.guillem.sample_app_rickandmorty.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.guillem.sample_app_rickandmorty.domain.model.Character

interface CharactersRepository {
    fun getCharacters(characterName: String): Flow<PagingData<Character>>
}