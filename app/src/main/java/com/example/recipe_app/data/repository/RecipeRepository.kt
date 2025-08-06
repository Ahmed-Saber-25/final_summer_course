package com.example.recipe_app.data.repository

import com.example.recipe_app.data.remote.RecipeApiService
import com.example.recipe_app.data.model.RecipeResponse

class RecipeRepository(private val apiService: RecipeApiService) {

    suspend fun searchMeals(query: String): RecipeResponse {
        return apiService.getMeals(query)
    }

    suspend fun getRecipeById(id: String): RecipeResponse {
        return apiService.getRecipeById(id)
    }

    suspend fun getRecipesByCategory(category: String): RecipeResponse {
        return apiService.getRecipesByCategory(category)
    }

    suspend fun getRandomRecipe(): RecipeResponse {
        return apiService.getRandomRecipe()
    }
}
