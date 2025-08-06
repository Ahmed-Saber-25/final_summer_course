package com.example.recipe_app.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.recipe_app.data.model.FavoriteRecipe

@Dao
interface FavoriteRecipeDao {

    // Get all favorite recipes for a specific user
    @Query("SELECT * FROM favorite_recipes WHERE userEmail = :email")
    fun getFavoritesByUser(email: String): LiveData<List<FavoriteRecipe>>

    // Add a recipe to user's favorites
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(recipe: FavoriteRecipe)

    // Remove a specific recipe from favorites
    @Delete
    suspend fun removeFromFavorites(recipe: FavoriteRecipe)

    // Check if a specific recipe is favorited by a user
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_recipes WHERE id = :recipeId AND userEmail = :email)")
    suspend fun isFavorite(recipeId: String, email: String): Boolean

    @Query("SELECT * FROM favorite_recipes WHERE userEmail = :email")
    suspend fun getFavoritesByEmail(email: String): List<FavoriteRecipe>

    // Get favorite by ID and user
    @Query("SELECT * FROM favorite_recipes WHERE id = :id AND userEmail = :email LIMIT 1")
    suspend fun getFavoriteById(id: String, email: String): FavoriteRecipe?
}
