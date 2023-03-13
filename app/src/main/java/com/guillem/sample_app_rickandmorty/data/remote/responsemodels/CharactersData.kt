package com.guillem.sample_app_rickandmorty.data.remote.responsemodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharactersData(
    @SerialName("info")
    val info: InfoData,
    @SerialName("results")
    val results: List<ResultData>
)