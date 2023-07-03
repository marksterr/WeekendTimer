package com.example.weekendtimer.data.remote.dtos.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDTO(
    val idCategory: String,
    val strCategory: String,
    val strCategoryDescription: String,
    val strCategoryThumb: String
)