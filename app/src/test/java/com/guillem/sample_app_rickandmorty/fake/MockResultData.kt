package com.guillem.sample_app_rickandmorty.fake

import com.guillem.sample_app_rickandmorty.data.local.entity.CharacterEntity
import com.guillem.sample_app_rickandmorty.data.remote.responsemodels.LocationData
import com.guillem.sample_app_rickandmorty.data.remote.responsemodels.OriginData
import com.guillem.sample_app_rickandmorty.data.remote.responsemodels.ResultData
import com.guillem.sample_app_rickandmorty.domain.model.Character
import com.guillem.sample_app_rickandmorty.ui.model.CharacterUI
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


val mockResultData = ResultData(
    created = "2017-11-04T18:50:21.651Z",
    episode = listOf(
        "https://rickandmortyapi.com/api/episode/1",
        "https://rickandmortyapi.com/api/episode/2",
        "https://rickandmortyapi.com/api/episode/3",
        "https://rickandmortyapi.com/api/episode/4",
        "https://rickandmortyapi.com/api/episode/5",
        "https://rickandmortyapi.com/api/episode/6",
        "https://rickandmortyapi.com/api/episode/7",
        "https://rickandmortyapi.com/api/episode/8",
        "https://rickandmortyapi.com/api/episode/9",
        "https://rickandmortyapi.com/api/episode/10",
        "https://rickandmortyapi.com/api/episode/11",
        "https://rickandmortyapi.com/api/episode/12",
        "https://rickandmortyapi.com/api/episode/13",
        "https://rickandmortyapi.com/api/episode/14",
        "https://rickandmortyapi.com/api/episode/15",
        "https://rickandmortyapi.com/api/episode/16",
        "https://rickandmortyapi.com/api/episode/17",
        "https://rickandmortyapi.com/api/episode/18",
        "https://rickandmortyapi.com/api/episode/19",
        "https://rickandmortyapi.com/api/episode/20",
        "https://rickandmortyapi.com/api/episode/21",
        "https://rickandmortyapi.com/api/episode/22",
        "https://rickandmortyapi.com/api/episode/23",
        "https://rickandmortyapi.com/api/episode/24",
        "https://rickandmortyapi.com/api/episode/25",
        "https://rickandmortyapi.com/api/episode/26",
        "https://rickandmortyapi.com/api/episode/27",
        "https://rickandmortyapi.com/api/episode/28",
        "https://rickandmortyapi.com/api/episode/29",
        "https://rickandmortyapi.com/api/episode/30",
        "https://rickandmortyapi.com/api/episode/31",
        "https://rickandmortyapi.com/api/episode/32",
        "https://rickandmortyapi.com/api/episode/33",
        "https://rickandmortyapi.com/api/episode/34",
        "https://rickandmortyapi.com/api/episode/35",
        "https://rickandmortyapi.com/api/episode/36",
        "https://rickandmortyapi.com/api/episode/37",
        "https://rickandmortyapi.com/api/episode/38",
        "https://rickandmortyapi.com/api/episode/39",
        "https://rickandmortyapi.com/api/episode/40",
        "https://rickandmortyapi.com/api/episode/41",
        "https://rickandmortyapi.com/api/episode/42",
        "https://rickandmortyapi.com/api/episode/43",
        "https://rickandmortyapi.com/api/episode/44",
        "https://rickandmortyapi.com/api/episode/45",
        "https://rickandmortyapi.com/api/episode/46",
        "https://rickandmortyapi.com/api/episode/47",
        "https://rickandmortyapi.com/api/episode/48",
        "https://rickandmortyapi.com/api/episode/49",
        "https://rickandmortyapi.com/api/episode/50",
        "https://rickandmortyapi.com/api/episode/51",
    ),
    gender = "Male",
    id = 2,
    image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
    location = LocationData(
        name = "Citadel of Ricks",
        url = "https://rickandmortyapi.com/api/location/3",
    ),
    name = "Morty Smith",
    origin = OriginData(
        name = "unknown",
        url = "null",
    ),
    species = "Human",
    status = "Alive",
    type = "",
    url = "https://rickandmortyapi.com/api/character/2"
)


fun getMockCharacterUI() = CharacterUI(
    gender = "Male",
    id = 0,
    image = "https://pics.filmaffinity.com/Rick_y_Morty_Serie_de_TV-157026175-large.jpg",
    location = "Earth",
    name = "Rick",
    numberOfEpisodesWhichAppears = 120,
    origin = "Earth",
    species = "Human",
    status = "Alive"
)

fun getMockCharacterEncodedJson(): String = Json.encodeToString(getMockCharacterUI())

fun getMockCharacterList() = listOf(
    Character(
        gender = "Male",
        id = 0,
        image = "https://pics.filmaffinity.com/Rick_y_Morty_Serie_de_TV-157026175-large.jpg",
        location = "Earth",
        name = "Mory",
        numberOfEpisodesWhichAppears = 120,
        origin = "Earth",
        species = "Human",
        status = "Alive"
    ),
    Character(
        gender = "Male",
        id = 134,
        image = "https://rickandmortyapi.com/api/character/avatar/134.jpeg",
        location = "Interdimensional Cable",
        name = "Garmanarnar",
        numberOfEpisodesWhichAppears = 1,
        origin = "unknown",
        species = "Alien",
        status = "Alive"
    ),
)

fun getMockCharacterEntity() = CharacterEntity(
    gender = "Male",
    id = 0,
    image = "https://pics.filmaffinity.com/Rick_y_Morty_Serie_de_TV-157026175-large.jpg",
    location = "Earth",
    name = "Rick",
    numberOfEpisodesWhichAppears = 120,
    origin = "Earth",
    species = "Human",
    status = "Alive"
)

