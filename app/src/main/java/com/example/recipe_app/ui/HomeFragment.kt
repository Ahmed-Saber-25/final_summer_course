package com.example.recipe_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe_app.adapter.RecipeAdapter
import com.example.recipe_app.databinding.FragmentHomeBinding
import com.example.recipe_app.viewmodel.RecipeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fetch recipes from API
        viewModel.fetchRecipes("chicken")

        // Observe recipe data
        viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            binding.recipeRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = RecipeAdapter(
                    recipes = recipes,
                    onItemClick = { selected ->
                        val action = HomeFragmentDirections
                            .actionHomeFragmentToRecipeDetailFragment(selected)
                        findNavController().navigate(action)
                    },
                    onFavoriteClick = { recipe, isFav ->
                        viewModel.toggleFavorite(recipe)
                    },
                    isFavorite = { recipe ->
                        viewModel.isFavorite(recipe)
                    }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
