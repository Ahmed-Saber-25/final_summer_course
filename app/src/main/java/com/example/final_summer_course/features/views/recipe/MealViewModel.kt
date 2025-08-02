package com.example.final_summer_course.features.views.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_summer_course.features.views.recipe.models.MealModel
import kotlinx.coroutines.launch

class MealViewModel(private val repository: MealRepository) : ViewModel() {

    private val _allMeals = MutableLiveData<List<MealModel>>()
    val allMeals: LiveData<List<MealModel>> get() = _allMeals

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    init {
        fetchAllMeals()
    }

    fun saveMeal(meal: MealModel) {
        viewModelScope.launch {
            repository.insertMeal(meal)
            fetchAllMeals()
        }
    }

    fun deleteMeal(meal: MealModel) {
        viewModelScope.launch {
            repository.deleteMeal(meal)
            fetchAllMeals()
        }
    }

    fun fetchAllMeals() {
        viewModelScope.launch {
            _allMeals.value = repository.getAllMeals()
        }
    }

    suspend fun isMealFavorite(id: String): Boolean {
        return repository.isMealFavorite(id)
    }
}