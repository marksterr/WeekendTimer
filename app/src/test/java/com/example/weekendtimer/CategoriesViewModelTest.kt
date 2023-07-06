package com.example.weekendtimer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weekendtimer.data.MealRepo
import com.example.weekendtimer.data.local.Category
import com.example.weekendtimer.ui.categories.CategoriesViewModel
import com.example.weekendtimer.util.CoroutineTestRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CategoriesViewModelTest {

    // Set up our rules for testing
    // We have a TestDispatcher to simulate coroutines during tests
    @get:Rule
    val testDispatcher = CoroutineTestRule()

    // This rule helps with testing LiveData
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // The ViewModel we're testing
    private lateinit var viewModel: CategoriesViewModel

    // We're mocking our MealRepo so we can control what it returns
    @Mock
    private lateinit var mockRepo: MealRepo

    // Setting up before each test
    @Before
    fun setup() {
        // OpenMocks helps us set up our mocked objects
        MockitoAnnotations.openMocks(this)
        // We initialize our ViewModel with our mocked MealRepo
        viewModel = CategoriesViewModel(mockRepo)
    }

    // Our test for the getCategories function
    @Test
    fun verifyGetCategories() = testDispatcher.dispatcher.runBlockingTest {
        // The response we're simulating from our mocked MealRepo
        val mockResponse = listOf(
            Category(
                idCategory = "1",
                strCategory = "Test Category",
                strCategoryDescription = "Test Description",
                strCategoryThumb = "Test Thumb"
            )
        )

        // We tell our mocked MealRepo to return our mockResponse when asked for categories
        `when`(mockRepo.getMealCategories()).thenReturn(mockResponse)

        viewModel.getCategories()

        // We advance time in our CoroutineTestRule until all jobs are complete
        testDispatcher.dispatcher.scheduler.advanceUntilIdle()

        // We expect our ViewModel's categories LiveData to contain the categories from our mockResponse
        assertEquals(mockResponse, viewModel.categories.value)
    }
}