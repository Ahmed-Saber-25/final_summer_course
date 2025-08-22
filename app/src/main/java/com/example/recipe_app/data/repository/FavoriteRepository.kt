package com.example.recipe_app.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.recipe_app.data.local.FavoriteRecipeDao
import com.example.recipe_app.data.model.FavoriteRecipe
import com.example.recipe_app.data.model.Recipe
import com.example.recipe_app.data.local.SharedPrefrence

class FavoriteRepository(
    private val favoriteRecipeDao: FavoriteRecipeDao,
    private val context: Context // ✅ Needed to get current user
) {

    private val sharedPref = SharedPrefrence(context)
    private val userEmail = sharedPref.getEmail() ?: ""

    fun getFavoritesByUser(): LiveData<List<FavoriteRecipe>> {
        return favoriteRecipeDao.getFavoritesByUser(userEmail)
    }

    // For internal/background use: used in coroutine scope
    suspend fun getFavoritesByEmail(email: String): List<FavoriteRecipe> {
        return favoriteRecipeDao.getFavoritesByEmail(email)
    }

    suspend fun addToFavorites(recipe: Recipe, userEmail: String) {
        val favoriteRecipe = FavoriteRecipe(
            id = recipe.idMeal,
            strMeal = recipe.strMeal,
            strMealThumb = recipe.strMealThumb,
            strInstructions = recipe.strInstructions,
            strYoutube = recipe.strYoutube,
            strCategory = recipe.strCategory,
            strArea = recipe.strArea,
            userEmail = userEmail // ✅ Save with user info
        )
        favoriteRecipeDao.addToFavorites(favoriteRecipe)
    }

    suspend fun removeFromFavorites(recipe: Recipe, userEmail: String) {
        val favoriteRecipe = FavoriteRecipe(
            id = recipe.idMeal,
            strMeal = recipe.strMeal,
            strMealThumb = recipe.strMealThumb,
            strInstructions = recipe.strInstructions,
            strYoutube = recipe.strYoutube,
            strCategory = recipe.strCategory,
            strArea = recipe.strArea,
            userEmail = userEmail // ✅ Match user when deleting
        )
        favoriteRecipeDao.removeFromFavorites(favoriteRecipe)
    }

    suspend fun isFavorite(id: String, userEmail: String): Boolean {
        return favoriteRecipeDao.getFavoriteById(id, userEmail) != null
    }
}
