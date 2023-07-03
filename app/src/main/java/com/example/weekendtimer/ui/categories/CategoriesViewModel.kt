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

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val repo: MealRepo) : ViewModel() {
    private val _categories: MutableLiveData<List<Category>> = MutableLiveData()
    val categories: LiveData<List<Category>> get() = _categories

    init {
        getCategories()
    }

    /**
     * Get characters.
     *
     */
    private fun getCategories() = viewModelScope.launch {
        _categories.value = repo.getMealCategories()
    }
}