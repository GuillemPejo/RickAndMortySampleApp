package com.guillem.sample_app_rickandmorty.core.utils

import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.test.TestDispatcher

fun <T> Flow<T>.slidingWindowPairs(): Flow<Pair<T?, T?>> =
    scan(Pair<T?, T?>(null, null)) { oldPair, newValue ->
        oldPair.second to newValue
    }.drop(1)

operator fun Flow<Boolean>.not() = map { !it }

fun <T, V> Flow<T>.mapLatestPeriodically(
    period: Long,
    action: suspend (T) -> V
): Flow<V> = flatMapLatest { value ->
    flow {
        while (true) {
            emit(action(value))
            delay(period)
        }
    }
}

fun <T> Flow<T>.sampleDynamic(millis: Flow<Long>): Flow<T> =
    millis.distinctUntilChanged().flatMapLatest { period ->
        sample(period)
    }

fun <T> Flow<T>.throttleLatestDynamic(millis: Flow<Long>): Flow<T> =
    millis.distinctUntilChanged().flatMapLatest { timeout ->
        throttleLatest(timeout)
    }

@OptIn(kotlin.ExperimentalStdlibApi::class)
fun <T> Flow<T>.throttleLatest(timeout: Long): Flow<T> {
    var time: Long? = null

    /**
     * Getting TestDispatcher's time if running on a test so that advancing time works
     */
    suspend fun getCurrentTime() = (coroutineContext[CoroutineDispatcher] as? TestDispatcher)
        ?.scheduler?.currentTime ?: System.currentTimeMillis()

    return mapLatest { value ->
        val currentTime = getCurrentTime()
        time?.let { time ->
            delay((timeout - (currentTime - time)).coerceAtLeast(0))
        }
        time = getCurrentTime()
        value
    }
}

fun <T, K> StateFlow<T>.mapState(
    scope: CoroutineScope,
    transform: (data: T) -> K,
    started: SharingStarted = SharingStarted.WhileSubscribed(),
): StateFlow<K> = mapLatest { transform(it) }
    .stateIn(scope, started, transform(value))
