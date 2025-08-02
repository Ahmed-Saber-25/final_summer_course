package com.example.final_summer_course.features.views.recipe.api

import com.example.final_summer_course.features.views.recipe.models.MealsResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface SimpleService {

    @GET("search.php")
    suspend fun searchMealByName(@Query("s") name: String): MealsResponse

    @GET("search.php")
    suspend fun listByFirstLetter(@Query("f") letter: String): MealsResponse
}
