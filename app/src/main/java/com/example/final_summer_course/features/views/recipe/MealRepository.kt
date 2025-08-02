package com.example.final_summer_course.features.views.recipe

import com.example.final_summer_course.features.views.recipe.database.MealDao
import com.example.final_summer_course.features.views.recipe.models.MealModel


interface MealRepository {
    suspend fun getAllMeals(): List<MealModel>
    suspend fun insertMeal(meal: MealModel)
    suspend fun deleteMeal(meal: MealModel)
    suspend fun isMealFavorite(id: String): Boolean
}

class MealRepositoryImpl(private val mealDao: MealDao) : MealRepository {

    override suspend fun getAllMeals(): List<MealModel> {
        return mealDao.getAll()
    }

    override suspend fun insertMeal(meal: MealModel) {
        return mealDao.insertMeal(meal)
    }

    override suspend fun deleteMeal(meal: MealModel) {
        return mealDao.deleteMeal(meal)
    }

    override suspend fun isMealFavorite(id: String): Boolean {
        return mealDao.isMealFavorite(id)
    }
}