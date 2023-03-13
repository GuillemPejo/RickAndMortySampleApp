package com.guillem.sample_app_rickandmorty.core.utils

import com.guillem.sample_app_rickandmorty.testfixtures.coroutines.test
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class FlowUtilsTest {
    private val dispatcher = StandardTestDispatcher()

    @Test
    fun `when a value is emitted, throttleLatest emits that value immediately`() = runTest(dispatcher) {
        val producer = flowOf(1)
        val startTime = dispatcher.scheduler.currentTime
        producer.throttleLatest(TIMEOUT).test(this).assertAtRest(1)
        val endTime = dispatcher.scheduler.currentTime
        assertEquals(LongWithMargin(0), LongWithMargin(endTime - startTime))
    }

    @Test
    fun `when a second value is emitted later than timeout, throttleLatest emits immediately that value`() = runTest(dispatcher) {
        val delaySecondEmission = TIMEOUT * 2
        val expected = listOf(1 to LongWithMargin(0), 2 to LongWithMargin(delaySecondEmission))
        val producer = flow {
            emit(1)
            advanceTimeBy(delaySecondEmission)
            emit(2)
        }
        val startTime = dispatcher.scheduler.currentTime
        producer
            .throttleLatest(TIMEOUT)
            .map { it to LongWithMargin(dispatcher.scheduler.currentTime - startTime) }
            .test(this)
            .assertExactlyAtRest(*expected.toTypedArray())
    }

    @Test
    fun `when 3 consecutive values are emitted, throttleLatest emits immediately the first, drops the second, and emits the last after timeout`() = runTest(dispatcher) {
        val producer = flowOf(1, 2, 3)
        val expected = listOf(1 to LongWithMargin(0), 3 to LongWithMargin(TIMEOUT))
        val startTime = dispatcher.scheduler.currentTime
        producer
            .throttleLatest(TIMEOUT)
            .map { it to LongWithMargin(dispatcher.scheduler.currentTime - startTime) }
            .test(this)
            .assertExactlyAtRest(*expected.toTypedArray())
    }
}

private class LongWithMargin(val value: Long, val margin: Long = TIME_MARGIN) {
    override fun equals(other: Any?): Boolean {
        if (other is LongWithMargin) {
            val maxMargin = margin.coerceAtLeast(other.margin)
            return other.value in (value - maxMargin) until (value + maxMargin)
        }
        return false
    }

    override fun toString(): String {
        return "${LongWithMargin::class.simpleName} value:$value, margin:$margin"
    }
}

private const val TIMEOUT = 300L
private const val TIME_MARGIN = 30L
