package com.guillem.sample_app_rickandmorty.data.remote.responsemodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OriginData(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)