package com.iti.myapplicationbnv.lec_8_9

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductsViewModel(val repository: ProductsRepository) : ViewModel() {
    private val _scoreA = MutableLiveData(0)
    val scoreA: LiveData<Int>
        get() = _scoreA

    private val _scoreB = MutableLiveData(0)
    val scoreB: LiveData<Int>
        get() = _scoreB

    val winnerMessage: MediatorLiveData<String> = MediatorLiveData()
    private val _productsApiResponse = MutableLiveData<List<ProductDM>?>()
    val productsResponse: LiveData<List<ProductDM>?>
        get() = _productsApiResponse

    init {
        winnerMessage.addSource(_scoreA) {
            updateWinnerMessage()
        }
        winnerMessage.addSource(_scoreB) {
            updateWinnerMessage()
        }
    }

    private fun updateWinnerMessage() {
        val scoreAValue = _scoreA.value ?: 0
        val scoreBValue = _scoreB.value ?: 0

        winnerMessage.value = if (scoreAValue >= 10 && scoreAValue > scoreBValue + 1) {
            "Team A Wins!"
        } else if (scoreBValue >= 10 && scoreBValue > scoreAValue + 1) {
            "Team B Wins!"
        } else {
            ""
        }
    }

    fun incrementScore(isTeamA: Boolean, points: Int) {
        if (isTeamA) {
            _scoreA.value = (_scoreA.value ?: 0) + points
        } else {
            _scoreB.value = (_scoreB.value ?: 0) + points
        }
    }

    fun resetScores() {
        _scoreA.value = 0
        _scoreB.value = 0
    }
    fun fetchProducts() {
        viewModelScope.launch {
            _productsApiResponse.value = repository.getAllProducts()
        }
    }
}
