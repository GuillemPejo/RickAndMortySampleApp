package com.guillem.sample_app_rickandmorty.domain.usecase

import androidx.paging.PagingData
import com.guillem.sample_app_rickandmorty.core.usecase.UseCaseSuspend
import com.guillem.sample_app_rickandmorty.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import com.guillem.sample_app_rickandmorty.domain.model.Character

interface GetCharactersUseCase : UseCaseSuspend<String, Flow<PagingData<Character>>>

class GetCharactersUseCaseImpl(
    private val charactersRepository: CharactersRepository,
) : GetCharactersUseCase {

    override suspend fun invoke(params: String): Flow<PagingData<Character>> {
        return charactersRepository.getCharacters(params)
    }
}
