package com.example.recipe_app.data.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_recipes")
data class FavoriteRecipe(
    @PrimaryKey  val id: String,
    val strMeal: String?,
    val strMealThumb: String?,
    val strInstructions: String?,
    val strYoutube: String?,
    val strCategory: String?,
    val strArea: String?,
    val userEmail: String
)
