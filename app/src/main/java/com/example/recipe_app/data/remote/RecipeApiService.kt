package com.example.recipe_app.data.remote

import com.example.recipe_app.data.model.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {

    // Example: https://www.themealdb.com/api/json/v1/1/search.php?s=chicken
    @GET("search.php")
    suspend fun getMeals(
        @Query("s") query: String
    ): RecipeResponse

    // Example: https://www.themealdb.com/api/json/v1/1/lookup.php?i=52772
    @GET("lookup.php")
    suspend fun getRecipeById(
        @Query("i") id: String
    ): RecipeResponse

    // Example: https://www.themealdb.com/api/json/v1/1/filter.php?c=Seafood
    @GET("filter.php")
    suspend fun getRecipesByCategory(
        @Query("c") category: String
    ): RecipeResponse

    // Example: https://www.themealdb.com/api/json/v1/1/random.php
    @GET("random.php")
    suspend fun getRandomRecipe(): RecipeResponse
}
