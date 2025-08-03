package com.example.recipe_app.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.recipe_app.databinding.FragmentRecipeDetailBinding
import com.example.recipe_app.model.Recipe

class RecipeDetailFragment : Fragment() {

    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var recipe: Recipe

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ✅ Retrieve the recipe from arguments
        recipe = arguments?.getParcelable("selectedRecipe")!!

        // ✅ Display recipe data
        binding.tvRecipeTitle.text = recipe.strMeal
        Glide.with(requireContext())
            .load(recipe.strMealThumb)
            .into(binding.ivRecipeImage)

        // ✅ Show brief instructions if available
        binding.tvInstructions.text = recipe.strInstructions ?: "No instructions available."

        // ✅ Handle YouTube video click
        binding.btnWatchVideo.setOnClickListener {
            val videoUrl = recipe.strYoutube
            if (!videoUrl.isNullOrEmpty()) {
                // Example: open video in external browser
                val intent = android.content.Intent(android.content.Intent.ACTION_VIEW)
                intent.data = android.net.Uri.parse(videoUrl)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
