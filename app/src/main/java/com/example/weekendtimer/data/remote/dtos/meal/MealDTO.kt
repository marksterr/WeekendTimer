package com.example.weekendtimer.data.remote.dtos.meal

import kotlinx.serialization.Serializable

@Serializable
data class MealDTO(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)