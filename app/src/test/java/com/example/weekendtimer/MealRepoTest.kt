package com.example.weekendtimer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weekendtimer.data.MealRepo
import com.example.weekendtimer.data.MealRepoImpl
import com.example.weekendtimer.data.remote.APIService
import com.example.weekendtimer.data.remote.dtos.category.CategoryDTO
import com.example.weekendtimer.data.remote.dtos.category.CategoryResponse
import com.example.weekendtimer.data.remote.dtos.meal.MealDTO
import com.example.weekendtimer.data.remote.dtos.meal.MealResponse
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MealRepoTest {

    // Setting up a test dispatcher for testing coroutines
    private val testDispatcher = StandardTestDispatcher()

    // Rule that swaps the background executor used by the Architecture Components with a different one which executes each task synchronously
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var mealRepo: MealRepo

    // Mock version of our APIService
    @Mock
    lateinit var apiService: APIService

    @Before
    fun setup() {
        // Initialize the mocks before each test
        MockitoAnnotations.openMocks(this)

        // Set the main dispatcher to our test dispatcher
        Dispatchers.setMain(testDispatcher)

        // Initialize the repository with our mock service
        mealRepo = MealRepoImpl(apiService)
    }

    // Testing successful fetch of meal categories
    @Test
    fun getMealCategories_Success() = runTest {
        // Prepare a mock response from the API
        val mockResponse = CategoryResponse(
            categories = listOf(
                CategoryDTO(
                    idCategory = "1",
                    strCategory = "Test Category",
                    strCategoryDescription = "Test Description",
                    strCategoryThumb = "Test Thumb"
                )
            )
        )

        // Mock the API's response
        `when`(apiService.getAllMealCategories()).thenReturn(mockResponse)

        // Call the actual method we're testing
        val result = mealRepo.getMealCategories()

        // Check if the result matches our expectation
        assertEquals(result[0].idCategory, mockResponse.categories[0].idCategory)
        assertEquals(result[0].strCategory, mockResponse.categories[0].strCategory)
        assertEquals(result[0].strCategoryDescription, mockResponse.categories[0].strCategoryDescription)
        assertEquals(result[0].strCategoryThumb, mockResponse.categories[0].strCategoryThumb)
    }

    // Testing successful fetch of meals in a category
    @Test
    fun getMealInCategory_Success() = runTest {
        val category = "Test Category"

        // Prepare a mock response from the API
        val mockResponse = MealResponse(
            meals = listOf(
                MealDTO(
                    idMeal = "1",
                    strMeal = "Test Meal",
                    strMealThumb = "Test Thumb"
                )
            )
        )

        // Mock the API's response
        `when`(apiService.getMealFromCategory(category)).thenReturn(mockResponse)

        // Call the actual method we're testing
        val result = mealRepo.getMealInCategory(category)

        // Check if the result matches our expectation
        assertEquals(result[0].idMeal, mockResponse.meals[0].idMeal)
        assertEquals(result[0].strMeal, mockResponse.meals[0].strMeal)
        assertEquals(result[0].strMealThumb, mockResponse.meals[0].strMealThumb)
    }

    // Testing failure in fetching meal categories
    @Test
    fun getMealCategories_Failure() = runTest {
        // Make the API throw an exception
        `when`(apiService.getAllMealCategories()).thenThrow(RuntimeException())

        try {
            // Call the actual method we're testing
            mealRepo.getMealCategories()

            // If the method did not throw an exception, the test failed
            fail("RuntimeException expected")
        } catch (e: Exception) {
            // If an exception was thrown, the test passed
        }
    }

    // Testing failure in fetching meals in a category
    @Test
    fun getMealInCategory_Failure() = runTest {
        val category = "Test Category"

        // Make the API throwan exception
        `when`(apiService.getMealFromCategory(category)).thenThrow(RuntimeException())

        try {
            // Call the actual method we're testing
            mealRepo.getMealInCategory(category)

            // If the method did not throw an exception, the test failed
            fail("RuntimeException expected")
        } catch (e: Exception) {
            // If an exception was thrown, the test passed
        }
    }

    // Testing behavior when API returns an empty list of meal categories
    @Test
    fun getMealCategories_EmptyResponse() = runTest {
        // Prepare a mock response with an empty list
        val mockResponse = CategoryResponse(categories = emptyList())

        // Mock the API's response
        `when`(apiService.getAllMealCategories()).thenReturn(mockResponse)

        // Call the actual method we're testing
        val result = mealRepo.getMealCategories()

        // The result should be an empty list
        assertTrue(result.isEmpty())
    }

    // Testing behavior when API returns an empty list of meals in a category
    @Test
    fun getMealInCategory_EmptyResponse() = runTest {
        val category = "Test Category"

        // Prepare a mock response with an empty list
        val mockResponse = MealResponse(meals = emptyList())

        // Mock the API's response
        `when`(apiService.getMealFromCategory(category)).thenReturn(mockResponse)

        // Call the actual method we're testing
        val result = mealRepo.getMealInCategory(category)

        // The result should be an empty list
        assertTrue(result.isEmpty())
    }

    // Testing behavior when a different exception is thrown while fetching meal categories
    @Test
    fun getMealCategories_DifferentException() = runTest {
        // Make the API throw an IllegalArgumentException
        `when`(apiService.getAllMealCategories()).thenThrow(IllegalArgumentException())

        try {
            // Call the actual method we're testing
            mealRepo.getMealCategories()

            // If the method did not throw an exception, the test failed
            fail("IllegalArgumentException expected")
        } catch (e: Exception) {
            // If an IllegalArgumentException was thrown, the test passed
            assertTrue(e is IllegalArgumentException)
        }
    }

    // Testing behavior when a different exception is thrown while fetching meals in a category
    @Test
    fun getMealInCategory_DifferentException() = runTest {
        val category = "Test Category"

        // Make the API throw an IllegalArgumentException
        `when`(apiService.getMealFromCategory(category)).thenThrow(IllegalArgumentException())

        try {
            // Call the actual method we're testing
            mealRepo.getMealInCategory(category)

            // If the method did not throw an exception, the test failed
            fail("IllegalArgumentException expected")
        } catch (e: Exception) {
            // If an IllegalArgumentException was thrown, the test passed
            assertTrue(e is IllegalArgumentException)
        }
    }
}