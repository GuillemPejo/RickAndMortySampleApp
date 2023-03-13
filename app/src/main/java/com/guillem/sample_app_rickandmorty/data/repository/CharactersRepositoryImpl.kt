package com.guillem.sample_app_rickandmorty.data.repository

import com.guillem.sample_app_rickandmorty.domain.repository.CharactersRepository
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.guillem.sample_app_rickandmorty.data.local.AppDatabase
import com.guillem.sample_app_rickandmorty.data.remote.ApiEndpoints
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.guillem.sample_app_rickandmorty.domain.model.Character


@OptIn(ExperimentalPagingApi::class)
class CharactersRepositoryImpl(
    private val api: ApiEndpoints,
    private val db : AppDatabase
) : CharactersRepository {

    override fun getCharacters(characterName: String): Flow<PagingData<Character>> {
        val pagingSourceFactory = { db.charactersDao().getCharactersByName(characterName) }

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                jumpThreshold = Int.MIN_VALUE,
                enablePlaceholders = true,
            ),
            remoteMediator = CharactersRemoteMediator(
                api,
                db,
                characterName
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { CharacterEntityPagingData ->
            CharacterEntityPagingData.map { characterEntity -> characterEntity.toDomain() }
        }
    }
}


