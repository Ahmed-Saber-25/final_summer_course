package com.example.recipe_app.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe_app.adapter.FavoritesAdapter
import com.example.recipe_app.data.model.RecipeDatabase
import com.example.recipe_app.data.model.FavoriteRecipe
import com.example.recipe_app.data.repository.FavoriteRepository
import com.example.recipe_app.databinding.FragmentFavoriteBinding
import com.example.recipe_app.viewmodel.FavoritesViewModel
import com.example.recipe_app.viewmodel.FavoritesViewModelFactory

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var favoritesViewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize DAO, repository, and ViewModel
        val dao = RecipeDatabase.getDatabase(requireContext().applicationContext).favoriteRecipeDao()
        val favoriteRepository = FavoriteRepository(dao, requireContext())
        val factory = FavoritesViewModelFactory(favoriteRepository, requireContext()) // ✅ FIXED

        favoritesViewModel = ViewModelProvider(this, factory).get(FavoritesViewModel::class.java)

        // Initialize adapter
        favoritesAdapter = FavoritesAdapter(emptyList()) { favorite ->
            // Handle favorite item click if needed
        }

        // Setup RecyclerView
        binding.favoriteRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoritesAdapter
        }

        observeFavorites()
    }

    private fun observeFavorites() {
        favoritesViewModel.allFavorites.observe(viewLifecycleOwner) { favorites: List<FavoriteRecipe> ->
            favoritesAdapter.updateData(favorites)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
