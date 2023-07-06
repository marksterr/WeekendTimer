package com.example.weekendtimer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weekendtimer.data.MealRepo
import com.example.weekendtimer.data.local.Meal
import com.example.weekendtimer.ui.meals.MealsViewModel
import com.example.weekendtimer.util.CoroutineTestRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MealsViewModelTest {

    // Set up our rules for testing
    // We have a CoroutineTestRule to simulate coroutines during tests
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    // This rule helps with testing LiveData
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // The ViewModel we're testing
    private lateinit var viewModel: MealsViewModel

    // We're mocking our MealRepo so we can control what it returns
    @Mock
    private lateinit var mockRepo: MealRepo

    // Setting up before each test
    @Before
    fun setup() {
        // OpenMocks helps us set up our mocked objects
        MockitoAnnotations.openMocks(this)
        viewModel = MealsViewModel(mockRepo)
    }

    // Our test for the getMeals function
    @Test
    fun verifyGetMeals() = coroutineTestRule.dispatcher.runBlockingTest {
        // The category of meals we're testing
        val testCategory = "Test Category"
        // The response we're simulating from our mocked MealRepo
        val mockResponse = listOf(
            Meal("1", "Test Meal", "Test Thumb")
        )

        // We tell our mocked MealRepo to return our mockResponse when asked for meals in our test category
        `when`(mockRepo.getMealInCategory(testCategory)).thenReturn(mockResponse)

        // We ask our ViewModel to get meals in our test category
        viewModel.getMeals(testCategory)

        // We advance time in our CoroutineTestRule until all jobs are complete
        coroutineTestRule.dispatcher.scheduler.advanceUntilIdle()

        // We expect our ViewModel's meals LiveData to contain the meals from our mockResponse
        assertEquals(mockResponse, viewModel.meals.value)
    }
}