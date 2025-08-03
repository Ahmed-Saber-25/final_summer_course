package com.example.final_summer_course.features.views.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_summer_course.features.views.recipe.models.MealModel
import kotlinx.coroutines.launch

class MealViewModel(private val repository: MealRepository) : ViewModel() {

    val allMeals: LiveData<List<MealModel>> = repository.getAllMeals() // ✅ Real-time

    fun saveMeal(meal: MealModel) {
        viewModelScope.launch {
            repository.insertMeal(meal)
        }
    }

    fun deleteMeal(meal: MealModel) {
        viewModelScope.launch {
            repository.deleteMeal(meal)
        }
    }

    suspend fun isMealFavorite(id: String): Boolean {
        return repository.isMealFavorite(id)
    }
}
