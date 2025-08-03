package com.example.recipe_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.model.Recipe
import com.example.recipe_app.network.RetrofitInstance
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> get()  = _recipes

    fun fetchRecipes(query: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getMeals(query)
                _recipes.value = response.meals ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
                _recipes.value = emptyList()
            }
        }
    }
}
