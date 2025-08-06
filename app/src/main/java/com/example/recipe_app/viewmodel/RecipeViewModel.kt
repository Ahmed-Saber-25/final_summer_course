package com.example.recipe_app.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.recipe_app.data.local.SharedPrefrence
import com.example.recipe_app.data.model.RecipeDatabase
import com.example.recipe_app.data.model.FavoriteRecipe
import com.example.recipe_app.data.model.Recipe
import com.example.recipe_app.data.repository.FavoriteRepository
import com.example.recipe_app.data.repository.RecipeRepository
import com.example.recipe_app.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    // Repositories
    private val recipeRepository = RecipeRepository(RetrofitInstance.api)
    private val favoriteRepository: FavoriteRepository

    // Logged-in user email
    private val sharedPref = SharedPrefrence(application)
    private val userEmail = sharedPref.getEmail() ?: ""

    // LiveData for all recipes fetched from API
    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> = _recipes

    // LiveData for favorite recipes filtered by user
    private val _favoriteRecipes = MutableLiveData<List<FavoriteRecipe>>()
    val favoriteRecipes: LiveData<List<FavoriteRecipe>> = _favoriteRecipes

    init {
        val dao = RecipeDatabase.getDatabase(application).favoriteRecipeDao()
        favoriteRepository = FavoriteRepository(dao, application) // ✅ Pass context here
        loadFavoritesForUser()
    }

    private fun loadFavoritesForUser() {
        viewModelScope.launch {
            val allFavorites = favoriteRepository.getFavoritesByEmail(userEmail)
            _favoriteRecipes.postValue(allFavorites)
        }
    }

    fun fetchRecipes(query: String = "") {
        viewModelScope.launch {
            try {
                val response = if (query.isBlank()) {
                    recipeRepository.getRandomRecipe()
                } else {
                    recipeRepository.searchMeals(query)
                }

                _recipes.value = response.meals ?: emptyList()

            } catch (e: IOException) {
                e.printStackTrace()
                _recipes.value = emptyList()
            } catch (e: HttpException) {
                e.printStackTrace()
                _recipes.value = emptyList()
            }
        }
    }

    fun addToFavorites(recipe: Recipe) {
        viewModelScope.launch {
            favoriteRepository.addToFavorites(recipe, userEmail)
            loadFavoritesForUser()
        }
    }

    fun removeFromFavorites(recipe: Recipe) {
        viewModelScope.launch {
            favoriteRepository.removeFromFavorites(recipe, userEmail)
            loadFavoritesForUser()
        }
    }

    fun toggleFavorite(recipe: Recipe) {
        viewModelScope.launch {
            val isFav = favoriteRepository.isFavorite(recipe.idMeal, userEmail)
            if (isFav) {
                removeFromFavorites(recipe)
            } else {
                addToFavorites(recipe)
            }
        }
    }

    fun isFavorite(recipe: Recipe): Boolean {
        return _favoriteRecipes.value?.any { it.id == recipe.idMeal } ?: false
    }
}
// 2222@22.com
// 3333