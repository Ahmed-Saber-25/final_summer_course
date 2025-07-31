package com.iti.myapplicationbnv.lec_10

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ColorDao {

    @Query("SELECT * FROM colors")
    suspend fun getAll(): List<Color>

    @Query("SELECT * FROM colors WHERE name = :id")
    fun getColorById(id: Int): LiveData<Color>

    @Insert
    suspend fun insert(vararg color: Color)

    @Update
    suspend fun update(color: Color)

    @Delete
    suspend fun delete(color: Color)
}