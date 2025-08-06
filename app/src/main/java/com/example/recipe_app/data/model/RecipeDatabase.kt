package com.example.recipe_app.data.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipe_app.data.local.UserDao
import com.example.recipe_app.data.model.User
import com.example.recipe_app.data.local.FavoriteRecipeDao
import com.example.recipe_app.data.model.FavoriteRecipe

@Database(entities = [User::class, FavoriteRecipe::class], version = 3, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun favoriteRecipeDao(): FavoriteRecipeDao

    companion object {
        @Volatile private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

// 555