package com.iti.myapplicationbnv.lec_10
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ColorViewModel(private val repository: ColorRepository) : ViewModel() {

    private val _allColors = MutableLiveData<List<Color>>()
    val allColors: LiveData<List<Color>>
        get() = _allColors

    init {
        fetchAllColors()
    }

    fun saveColor(color: Color) {
        viewModelScope.launch {
            repository.insertColor(color)
            fetchAllColors()
        }
    }

    fun updateColor(color: Color) {
        viewModelScope.launch {
            repository.updateColor(color)
            fetchAllColors()
        }
    }

    fun deleteColor(color: Color) {
        viewModelScope.launch {
            repository.deleteColor(color)
            fetchAllColors()
        }
    }

    fun fetchAllColors() {
        viewModelScope.launch {
            _allColors.value = repository.getAllColors()
        }
    }

    fun getColorByName(id: Int): LiveData<Color> {
        return repository.getColorById(id)
    }
}