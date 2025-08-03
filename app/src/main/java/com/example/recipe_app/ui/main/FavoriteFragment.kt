package com.example.recipe_app.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe_app.adapter.FavoritesAdapter
import com.example.recipe_app.databinding.FragmentFavoriteBinding
import com.example.recipe_app.model.Recipe
import androidx.recyclerview.widget.RecyclerView

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoritesAdapter: FavoritesAdapter
    private val favoriteRecipes = mutableListOf<Recipe>() // Temporary list, replace with Room data

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoritesAdapter = FavoritesAdapter(favoriteRecipes) {}
        binding.recyclerFavorites.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoritesAdapter
        }

        loadFavoriteRecipes()
    }

    private fun loadFavoriteRecipes() {
        // TODO: Replace with Room DB
        favoriteRecipes.add(
            Recipe(
                idMeal = "1",
                strMeal = "Spaghetti",
                strMealThumb = "https://www.themealdb.com/images/media/meals/58oia61564916529.jpg",
                strInstructions = "Boil pasta. Add sauce. Mix.",
                strYoutube = "https://youtube.com",
            )
        )
        favoritesAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
