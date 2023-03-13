package com.guillem.sample_app_rickandmorty.data.remote.responsemodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InfoData(
    @SerialName("count")
    val count: Int,
    @SerialName("next")
    val next: String?,
    @SerialName("pages")
    val pages: Int,
    @SerialName("prev")
    val prev: String?
)
