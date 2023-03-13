package com.guillem.sample_app_rickandmorty.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.guillem.sample_app_rickandmorty.domain.model.Character

@Entity(tableName = "Character")
data class CharacterEntity(
    val gender: String,
    @PrimaryKey val id: Int,
    val image: String,
    val location: String,
    val name: String,
    val numberOfEpisodesWhichAppears: Int,
    val origin: String,
    val species: String,
    val status: String,
) {
    fun toDomain(): Character = Character(
        gender = gender,
        id = id,
        image = image,
        location = location,
        name = name,
        numberOfEpisodesWhichAppears = numberOfEpisodesWhichAppears,
        origin = origin,
        species = species,
        status = status,
    )
}

