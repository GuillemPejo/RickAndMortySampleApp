package com.guillem.sample_app_rickandmorty.testfixtures.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import org.junit.Assert

@ExperimentalCoroutinesApi
class TestObserver<T>(
    private val testScope: TestScope,
    flow: Flow<T>
) {
    private val values = mutableListOf<T>()
    private val observer: Job = testScope.launch {
        flow.collect { values.add(it) }
    }

    fun assertNoValues(): TestObserver<T> {
        Assert.assertEquals(emptyList<T>(), this.values)
        return this
    }

    fun assertValues(vararg values: T): TestObserver<T> {
        Assert.assertEquals(values.toList(), this.values)
        return this
    }

    fun assert(assertion: (T) -> Boolean): TestObserver<T> {
        Assert.assertTrue(assertion(this.values.last()))
        return this
    }

    fun assertAtRest(value: T): TestObserver<T> {
        testScope.advanceUntilIdle()
        Assert.assertEquals(value, this.values.last())
        return this
    }

    fun assertAtRest(assertion: (T) -> Boolean): TestObserver<T> {
        testScope.advanceUntilIdle()
        Assert.assertTrue(assertion(this.values.last()))
        return this
    }

    fun assertExactlyAtRest(vararg values: T): TestObserver<T> {
        testScope.advanceUntilIdle()
        return assertValues(*values)
    }

    fun assertFinally(value: T) {
        testScope.advanceUntilIdle()
        Assert.assertEquals(value, this.values.last())
        finish()
    }

    /**
     * Some flow operators are never in idle state (like sample). For that reason using
     * advanceUntilIdle does not work, as the test never completes.
     * In these cases probably a specific approach needs to be used. This util may sometimes work
     */
    fun assertWhenMaybeIdle(expected: T, advanceMillis: Long = 10_000): TestObserver<T> {
        testScope.advanceTimeBy(advanceMillis)
        testScope.runCurrent()
        Assert.assertEquals(expected, this.values.last())
        return this
    }

    fun assertWhenMaybeIdle(
        advanceMillis: Long = 10_000,
        assertion: (T) -> Boolean,
    ): TestObserver<T> {
        testScope.advanceTimeBy(advanceMillis)
        testScope.runCurrent()
        Assert.assertTrue(assertion(this.values.last()))
        return this
    }

    fun finish(vararg scopes: CoroutineScope) {
        observer.cancel()
        scopes.forEach { it.cancel() }
    }
}

@ExperimentalCoroutinesApi
fun <T> Flow<T>.test(testScope: TestScope): TestObserver<T> {
    return TestObserver(testScope, this)
}
