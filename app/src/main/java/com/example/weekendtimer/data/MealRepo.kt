package com.example.weekendtimer.data

import com.example.weekendtimer.data.local.Category
import com.example.weekendtimer.data.local.Meal

interface MealRepo {
    suspend fun getMealCategories(): List<Category>
    suspend fun getMealInCategory(category: String): List<Meal>
}