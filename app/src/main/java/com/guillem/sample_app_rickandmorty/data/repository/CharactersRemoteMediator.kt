package com.guillem.sample_app_rickandmorty.data.repository


import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.guillem.sample_app_rickandmorty.data.local.entity.CharacterEntity
import com.guillem.sample_app_rickandmorty.data.local.entity.KeyEntity
import com.guillem.sample_app_rickandmorty.data.local.AppDatabase
import com.guillem.sample_app_rickandmorty.data.remote.ApiEndpoints
import retrofit2.HttpException
import java.io.IOException


@ExperimentalPagingApi
class CharactersRemoteMediator(
    private val api: ApiEndpoints,
    private val db: AppDatabase, private val characterName: String
) : RemoteMediator<Int, CharacterEntity>() {
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> return pageKeyData
            else -> pageKeyData as Int
        }

        try {
            val response = if (characterName.isEmpty()) api.getCharacters(page = page)
            else api.getCharactersByName(page = page, characterName)

            val isEndOfList = response.info.next == null || response.toString().contains("error") || response.results.isEmpty()

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.charactersDao().deleteAll()
                    db.keysDao().deleteAll()
                }
                val prevKey = if (page == 1) null else page.minus(1)
                val nextKey = if (isEndOfList) null else page.plus(1)
                val keys = response.results.map {
                    KeyEntity(it.id, prevKey = prevKey, nextKey = nextKey)
                }
                db.keysDao().insertAll(keys)
                db.charactersDao().insertCharacters(response.results.map { it.toCharacterEntity() })
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKeyPageData(
        loadType: LoadType, state: PagingState<Int, CharacterEntity>
    ): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = state.getRemoteKeyClosestToCurrentPosition()
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.APPEND -> {
                val remoteKeys = state.getLastRemoteKey()
                val nextKey = remoteKeys?.nextKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                val remoteKeys = state.getFirstRemoteKey()
                remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = false
                )
            }
        }
    }

    private suspend fun PagingState<Int, CharacterEntity>.getRemoteKeyClosestToCurrentPosition(): KeyEntity? =
        anchorPosition?.let { position ->
            closestItemToPosition(position)?.id?.let { repoId -> db.keysDao().getKeysByCharacterId(repoId) }
        }

    private suspend fun PagingState<Int, CharacterEntity>.getLastRemoteKey(): KeyEntity? =
        pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { character -> db.keysDao().getKeysByCharacterId(character.id) }

    private suspend fun PagingState<Int, CharacterEntity>.getFirstRemoteKey(): KeyEntity? =
        pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { character -> db.keysDao().getKeysByCharacterId(character.id) }

}
