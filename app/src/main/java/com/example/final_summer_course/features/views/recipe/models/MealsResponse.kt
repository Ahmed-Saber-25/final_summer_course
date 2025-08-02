package com.example.final_summer_course.features.views.recipe.models

import com.google.gson.annotations.SerializedName

data class MealsResponse(
    @SerializedName("meals")
    val meals: List<MealModel>
)