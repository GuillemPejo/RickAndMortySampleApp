package com.guillem.sample_app_rickandmorty.data.remote.responsemodels

import com.guillem.sample_app_rickandmorty.data.local.entity.CharacterEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ResultData(
    @SerialName("created") val created: String,
    @SerialName("episode") val episode: List<String>,
    @SerialName("gender") val gender: String,
    @SerialName("id") val id: Int,
    @SerialName("image") val image: String,
    @SerialName("location") val location: LocationData,
    @SerialName("name") val name: String,
    @SerialName("origin") val origin: OriginData,
    @SerialName("species") val species: String,
    @SerialName("status") val status: String,
    @SerialName("type") val type: String,
    @SerialName("url") val url: String
) {
    fun toCharacterEntity() = CharacterEntity(
        gender = gender,
        id = id,
        image = image,
        location = location.name,
        name = name,
        numberOfEpisodesWhichAppears = episode.size,
        origin = origin.name,
        species = species,
        status = status,
    )
}