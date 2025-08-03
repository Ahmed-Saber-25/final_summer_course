package com.example.final_summer_course.features.views.recipe.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.final_summer_course.features.views.recipe.models.MealModel

@Dao
interface MealDao {
    @Query("SELECT * FROM meals")
    fun getAllMeals(): LiveData<List<MealModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MealModel)

    @Delete
    suspend fun deleteMeal(meal: MealModel)

    @Query("SELECT EXISTS(SELECT 1 FROM meals WHERE id = :mealId)")
    suspend fun isMealFavorite(mealId: String): Boolean
}