package com.example.weekendtimer.data.remote

import com.example.weekendtimer.data.remote.dtos.category.CategoryResponse
import com.example.weekendtimer.data.remote.dtos.meal.MealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// This is an interface named 'APIService'.
// An interface in Kotlin is a blueprint for classes.
// It contains function signatures that a class implementing this interface must provide.
interface APIService {

    // Here is a function signature for a function named 'getAllMealCategories'.
    // The @GET annotation tells Retrofit that this is a GET request.
    // The function will retrieve all meal categories from the API.
    // 'CATEGORY_ENDPOINT' is the part of the URL that points to the desired API endpoint.
    // 'suspend' keyword means that this function is a coroutine and it can be paused and resumed.
    // The function returns a 'CategoryResponse', which is a class that will hold the response from the API.
    @GET(CATEGORY_ENDPOINT)
    suspend fun getAllMealCategories(): CategoryResponse

    @GET(MEAL_IN_CATEGORY_ENDPOINT)
    suspend fun getMealFromCategory(@Query("c") categoryName: String): MealResponse

    // This is a 'companion object'. In Kotlin, it is a way of creating 'static' members of the class.
    // These constants will be accessible from anywhere the APIService is used.
    companion object {
        private const val CATEGORY_ENDPOINT = "categories.php"
        private const val MEAL_IN_CATEGORY_ENDPOINT = "filter.php"
    }
}