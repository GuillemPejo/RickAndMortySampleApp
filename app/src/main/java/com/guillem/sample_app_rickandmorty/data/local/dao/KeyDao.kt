package com.guillem.sample_app_rickandmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.guillem.sample_app_rickandmorty.data.local.entity.KeyEntity

@Dao
interface KeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(key: List<KeyEntity>)

    @Query("SELECT * FROM Key WHERE characterId = :id")
    suspend fun getKeysByCharacterId(id: Int): KeyEntity?

    @Query("DELETE FROM Key")
    suspend fun deleteAll()
}