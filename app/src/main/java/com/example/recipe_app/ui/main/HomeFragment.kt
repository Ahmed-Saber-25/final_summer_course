package com.example.recipe_app.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe_app.adapter.HomeAdapter
import com.example.recipe_app.databinding.FragmentHomeBinding
import com.example.recipe_app.model.Recipe
import com.example.recipe_app.network.RetrofitInstance
import com.example.recipe_app.ui.HomeFragmentDirections
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeAdapter: HomeAdapter
    private val recipesList = mutableListOf<Recipe>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeAdapter = HomeAdapter(recipesList) { selectedRecipe ->
            val action = HomeFragmentDirections.actionHomeFragmentToRecipeDetailFragment(selectedRecipe)
            findNavController().navigate(action)
        }

        binding.recyclerHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeAdapter
        }

        fetchRecipes()
    }

    private fun fetchRecipes() {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.getMeals("")
                response.meals?.let {
                    recipesList.clear()
                    recipesList.addAll(it)
                    homeAdapter.notifyDataSetChanged()
                } ?: showToast("No meals found")
            } catch (e: Exception) {
                showToast("Error fetching recipes: ${e.localizedMessage}")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
