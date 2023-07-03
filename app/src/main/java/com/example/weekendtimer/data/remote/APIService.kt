package com.example.weekendtimer.data.remote

import com.example.weekendtimer.data.remote.dtos.category.CategoryResponse
import com.example.weekendtimer.data.remote.dtos.meal.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET(CATEGORY_ENDPOINT)
    suspend fun getAllMealCategories(): CategoryResponse

    @GET(MEAL_ENDPOINT)
    suspend fun getMeal(@Query("s") mealName: String): MealResponse

    companion object {
        private const val CATEGORY_ENDPOINT = "categories.php"
        private const val MEAL_ENDPOINT = "search.php"
    }
}