package com.guillem.sample_app_rickandmorty.ui.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterUI(
    val gender: String,
    val id: Int,
    val image: String,
    val location: String,
    val name: String,
    val numberOfEpisodesWhichAppears: Int,
    val origin: String,
    val species: String,
    val status: String
)
