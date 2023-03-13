package com.guillem.sample_app_rickandmorty.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.guillem.sample_app_rickandmorty.data.local.dao.CharactersDao
import com.guillem.sample_app_rickandmorty.data.local.dao.KeyDao
import com.guillem.sample_app_rickandmorty.data.local.entity.CharacterEntity
import com.guillem.sample_app_rickandmorty.data.local.entity.KeyEntity

private const val APP_DB_NAME = "rick_and_morty_database"


@Database(
    entities = [
        KeyEntity::class,
        CharacterEntity::class
    ], version = 1, exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun charactersDao(): CharactersDao
    abstract fun keysDao(): KeyDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) = Room
            .databaseBuilder(context.applicationContext, AppDatabase::class.java, APP_DB_NAME)
            .build()
    }
}