package com.example.weekendtimer.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weekendtimer.data.MealRepo
import com.example.weekendtimer.data.local.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// @HiltViewModel is an annotation which indicates that Hilt should provide some dependencies to this ViewModel.
@HiltViewModel
class CategoriesViewModel @Inject constructor(private val repo: MealRepo) : ViewModel() {
    // Mutable LiveData that holds a list of Category objects.
    private val _categories: MutableLiveData<List<Category>> = MutableLiveData()
    // Public immutable LiveData. The UI can observe this to get updates when the categories data changes.
    val categories: LiveData<List<Category>> get() = _categories

    // Initializer block. Calls getCategories method when ViewModel is created.
    init {
        getCategories()
    }

    // Asynchronous method to fetch categories from repository using coroutine launched from viewModelScope.
    fun getCategories() = viewModelScope.launch {
        _categories.value = repo.getMealCategories() // Update _categories value with data fetched from repository.
    }
}
