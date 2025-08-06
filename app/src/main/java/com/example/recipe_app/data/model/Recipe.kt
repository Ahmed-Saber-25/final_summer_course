package com.example.recipe_app.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
@Entity(tableName = "recipe")

@Parcelize
data class Recipe(
    @PrimaryKey val idMeal: String,
    val strMeal: String?,
    val strMealThumb: String,
    val strInstructions: String? = null,
    val strCategory: String? = null,
    val strArea: String? = null,

    val strYoutube: String? = null
) : Parcelable

data class MealResponse(
    val meals: List<Recipe>?
)

