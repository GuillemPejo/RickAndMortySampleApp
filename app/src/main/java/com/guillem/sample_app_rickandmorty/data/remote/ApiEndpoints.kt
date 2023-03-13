package com.guillem.sample_app_rickandmorty.data.remote

import com.guillem.sample_app_rickandmorty.data.remote.responsemodels.CharactersData
import retrofit2.http.GET
import retrofit2.http.Query

private const val CHARACTERS_ENDPOINT_PATH = "character/"

interface ApiEndpoints {
    @GET(CHARACTERS_ENDPOINT_PATH)
    suspend fun getCharacters(
        @Query("page") page: Int,
    ): CharactersData

    @GET(CHARACTERS_ENDPOINT_PATH)
    suspend fun getCharactersByName(
        @Query("page") page: Int,
        @Query("name") characterName: String
    ): CharactersData
}
