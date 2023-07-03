package com.example.weekendtimer.data

import com.example.weekendtimer.data.local.Category
import com.example.weekendtimer.data.remote.APIService
import com.example.weekendtimer.data.remote.dtos.category.CategoryDTO
import javax.inject.Inject

class MealRepo @Inject constructor(private val service: APIService) {

    suspend fun getMealCategories(): List<Category> {
        val categoryDTOs: List<CategoryDTO> = service.getAllMealCategories().categories
        return categoryDTOs.map {
            Category(
                idCategory = it.idCategory,
                strCategory = it.strCategory,
                strCategoryDescription = it.strCategoryDescription,
                strCategoryThumb = it.strCategoryThumb
            )
        }
    }
}