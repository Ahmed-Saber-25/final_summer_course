package com.example.recipe_app.network

import com.example.recipe_app.data.model.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {
    @GET("search.php")
    suspend fun getMeals(@Query("s") query: String): MealResponse
}