package com.example.weekendtimer.data.remote.dtos.meal

import kotlinx.serialization.Serializable

@Serializable
data class MealResponse(
    val meals: List<MealDTO>
)