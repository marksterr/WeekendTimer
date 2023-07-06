package com.example.weekendtimer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weekendtimer.ui.home.HomeViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    // Set up a rule to use the InstantTaskExecutorRule, which allows LiveData to work properly in tests
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Define the ViewModel to be tested
    lateinit var viewModel: HomeViewModel

    // Use the @Before annotation to indicate this setup function should run before each test
    @Before
    fun setup() {
        // Initialize the ViewModel
        viewModel = HomeViewModel()
    }

    // Test that the initial text is correct
    @Test
    fun verifyInitialText() {
        // Define the expected initial text
        val expectedInitialText = "This is an app that displays a list of meal categories"

        // Assert that the ViewModel's text matches the expected value
        assertEquals(expectedInitialText, viewModel.text.value)
    }
}