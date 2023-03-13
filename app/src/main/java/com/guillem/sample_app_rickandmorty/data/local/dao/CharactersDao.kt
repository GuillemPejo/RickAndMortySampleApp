package com.guillem.sample_app_rickandmorty.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.guillem.sample_app_rickandmorty.data.local.entity.CharacterEntity

@Dao
interface CharactersDao {

    @Query("SELECT * FROM Character WHERE name LIKE '%' || :characterName || '%'")
    fun getCharactersByName(characterName: String): PagingSource<Int, CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterEntity>)

    @Query("DELETE FROM Character")
    suspend fun deleteAll()
}
