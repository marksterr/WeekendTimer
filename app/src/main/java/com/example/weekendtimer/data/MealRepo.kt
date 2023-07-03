package com.example.weekendtimer.data

import com.example.weekendtimer.data.local.Category
import com.example.weekendtimer.data.remote.APIService
import com.example.weekendtimer.data.remote.dtos.category.CategoryDTO
import javax.inject.Inject

// We are defining a class called 'MealRepo'. This class will be responsible for getting data from the API.
// The class constructor takes an 'APIService' as a parameter. This service is provided using Dagger's dependency injection,
// as indicated by the '@Inject' annotation.
class MealRepo @Inject constructor(private val service: APIService) {

    // This function 'getMealCategories' fetches the meal categories from the API using the APIService.
    // The function is a coroutine, as indicated by the 'suspend' keyword. This means it can perform long running operations like
    // network requests without blocking the main thread, thus not freezing the app.
    suspend fun getMealCategories(): List<Category> {
        // Here we are calling the function 'getAllMealCategories' from our APIService.
        // It returns a 'CategoryResponse' object, and we are extracting the 'categories' property, which is a list of 'CategoryDTO' objects.
        val categoryDTOs: List<CategoryDTO> = service.getAllMealCategories().categories

        // Then we are mapping each 'CategoryDTO' to a 'Category' object.
        // 'map' is a function that transforms each element in a list.
        // In this case, we are creating a new 'Category' object for each 'CategoryDTO' in our list.
        // We are returning this list of 'Category' objects.
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
