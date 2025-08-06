// com/example/recipe_app/data/model/User.kt
package com.example.recipe_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val email: String,
    val password: String
)
