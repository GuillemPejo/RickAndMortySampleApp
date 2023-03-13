package com.guillem.sample_app_rickandmorty.commonsui.navigation

import kotlin.reflect.KType
import kotlin.reflect.typeOf
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> buildNavSerializer(
    crossinline serializer: (T) -> String = { Json.encodeToString(it) },
): Pair<KType, (Any) -> String?> =
    typeOf<T>() to { instance ->
        (instance as? T)?.let(serializer)
    }

inline fun <reified T> buildNavDeserializer(
    crossinline deserializer: (String) -> T = { Json.decodeFromString(it) },
): Pair<KType, (String) -> Any?> =
    typeOf<T>() to { serialized ->
        deserializer(serialized)
    }
