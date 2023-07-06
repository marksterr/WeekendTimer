package com.example.weekendtimer.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class CoroutineTestRule : TestWatcher() {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    val dispatcher: TestCoroutineDispatcher get() = testCoroutineDispatcher

    @ExperimentalCoroutinesApi
    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @ExperimentalCoroutinesApi
    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }
}