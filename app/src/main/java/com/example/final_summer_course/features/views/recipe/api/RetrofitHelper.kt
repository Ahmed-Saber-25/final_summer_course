package com.example.final_summer_course.features.views.recipe.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private val baseURL = "https://www.themealdb.com/api/json/v1/1/"

    val instance: SimpleService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(SimpleService::class.java)
    }
}