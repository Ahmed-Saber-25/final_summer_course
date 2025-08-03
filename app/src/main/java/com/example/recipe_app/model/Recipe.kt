package com.example.recipe_app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val strInstructions: String? = null,
    val strYoutube: String? = null,
    val name: String? = null,
    val imageUrl: String? = null


) : Parcelable

data class MealResponse(
    val meals: List<Recipe>?
)
