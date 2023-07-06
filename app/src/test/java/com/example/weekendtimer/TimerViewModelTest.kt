package com.example.weekendtimer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weekendtimer.ui.timer.TimerViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TimerViewModelTest {

    // Set up a rule to use the InstantTaskExecutorRule, which allows LiveData to work properly in tests
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Define a dispatcher for controlling coroutines in testing
    private val testDispatcher = StandardTestDispatcher()

    // Define the ViewModel to be tested
    lateinit var viewModel: TimerViewModel

    // Use the @Before annotation to indicate this setup function should run before each test
    @Before
    fun setup() {
        // Set the main dispatcher to our test dispatcher
        Dispatchers.setMain(testDispatcher)

        // Initialize the ViewModel
        viewModel = TimerViewModel()
    }

    // Test that the initial countdown display is correct
    @Test
    fun verifyCountdownDisplay_InitialState() = runTest {
        // Define the expected initial countdown display
        val expectedInitialValue = "3:000"

        // Assert that the ViewModel's countdown display matches the expected value
        assertEquals(expectedInitialValue, viewModel.countdownDisplay.value)
    }

    // Test that the initial navigation flag is correct
    @Test
    fun verifyNavigateToMain_InitialState() = runTest {
        // Define the expected initial navigation flag
        val expectedInitialValue = false

        // Assert that the ViewModel's navigation flag matches the expected value
        assertEquals(expectedInitialValue, viewModel.navigateToMain.value)
    }

    // Test that the navigation flag is set after the countdown finishes
    @Test
    fun verifyNavigateToMain_AfterCountdown() = runTest {
        // Start the countdown
        viewModel.startCountdown()

        // Wait for a bit longer than the countdown time, to let it finish
        delay(3100)

        // Assert that the navigation flag is now true
        assertTrue(viewModel.navigateToMain.value!!)
    }

    // Test that the navigation flag is reset after navigation is done
    @Test
    fun verifyNavigateToMain_AfterNavigation() = runTest {
        // Indicate that navigation is done
        viewModel.onMainNavigated()

        // Assert that the navigation flag is now false
        assertFalse(viewModel.navigateToMain.value!!)
    }

    // Use the @After annotation to indicate this cleanup function should run after each test
    @After
    fun cleanup() {
        // Cancel any running countdowns
        viewModel.onCleared()
    }
}