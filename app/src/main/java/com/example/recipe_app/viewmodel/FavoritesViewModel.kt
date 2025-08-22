package com.example.recipe_app.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.recipe_app.data.local.SharedPrefrence
import com.example.recipe_app.data.model.FavoriteRecipe
import com.example.recipe_app.data.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: FavoriteRepository,
    context: Context
) : ViewModel() {

    private val userEmail: String = SharedPrefrence(context).getEmail() ?: ""

    private val _allFavorites = MutableLiveData<List<FavoriteRecipe>>()
    val allFavorites: LiveData<List<FavoriteRecipe>> = _allFavorites

    init {
        getFavoritesForUser()
    }

    private fun getFavoritesForUser() {
        viewModelScope.launch {
            Log.d("FAV_DEBUG", "User email: $userEmail")
            val favorites = repository.getFavoritesByEmail(userEmail)
            Log.d("FAV_DEBUG", "Fetched favorites count: ${favorites.size}")
            _allFavorites.postValue(favorites)
        }
    }

    suspend fun isFavorite(recipeId: String): Boolean {
        return repository.isFavorite(recipeId, userEmail)
    }

    class FavoritesViewModelFactory(
        private val repository: FavoriteRepository,
        private val context: Context
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FavoritesViewModel(repository, context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
