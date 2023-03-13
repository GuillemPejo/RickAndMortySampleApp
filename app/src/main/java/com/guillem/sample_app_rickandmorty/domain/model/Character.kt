package com.guillem.sample_app_rickandmorty.domain.model

import com.guillem.sample_app_rickandmorty.ui.model.CharacterUI
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val gender: String,
    val id: Int,
    val image: String,
    val location: String,
    val name: String,
    val numberOfEpisodesWhichAppears: Int,
    val origin: String,
    val species: String,
    val status: String,
) {
    fun toUi() = CharacterUI(
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