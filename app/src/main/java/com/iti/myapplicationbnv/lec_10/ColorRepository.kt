package com.iti.myapplicationbnv.lec_10

import androidx.lifecycle.LiveData

interface ColorRepository {
    suspend fun getAllColors(): List<Color>
    fun getColorById(id: Int): LiveData<Color>
    suspend fun insertColor(color: Color)
    suspend fun updateColor(color: Color)
    suspend fun deleteColor(color: Color)
}
class ColorRepositoryImpl(private val colorDao: ColorDao) : ColorRepository {
    override suspend fun getAllColors(): List<Color> {
        return colorDao.getAll()
    }

    override fun getColorById(id: Int): LiveData<Color> {
        return colorDao.getColorById(id)
    }

    override suspend fun insertColor(color: Color) {
        colorDao.insert(color)
    }

    override suspend fun updateColor(color: Color) {
        colorDao.update(color)
    }

    override suspend fun deleteColor(color: Color) {
        colorDao.delete(color)
    }
}