package com.example.weekendtimer.data.remote.dtos.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val categories: List<CategoryDTO> = emptyList()
)