package com.example.weekendtimer.ui.meals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weekendtimer.data.MealRepo
import com.example.weekendtimer.data.local.Category
import com.example.weekendtimer.data.local.Meal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealsViewModel @Inject constructor(private val repo: MealRepo) : ViewModel() {

    private val _meals: MutableLiveData<List<Meal>> = MutableLiveData()
    val meals: LiveData<List<Meal>> get() = _meals

    fun getMeals(category: String) = viewModelScope.launch {
        _meals.value = repo.getMealInCategory(category)
    }
}