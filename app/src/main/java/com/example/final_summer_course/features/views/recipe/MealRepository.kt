package com.example.final_summer_course.features.views.recipe

import androidx.lifecycle.LiveData
import com.example.final_summer_course.features.views.recipe.database.MealDao
import com.example.final_summer_course.features.views.recipe.models.MealModel


interface MealRepository {
    fun getAllMeals(): LiveData<List<MealModel>>
    suspend fun insertMeal(meal: MealModel)
    suspend fun deleteMeal(meal: MealModel)
    suspend fun isMealFavorite(id: String): Boolean
    suspend fun deleteAllMeals()
}

class MealRepositoryImpl(private val mealDao: MealDao) : MealRepository {

    override fun getAllMeals(): LiveData<List<MealModel>> {
        return mealDao.getAllMeals()
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

    override suspend fun deleteAllMeals() {
        return mealDao.deleteAllMeals()
    }
}