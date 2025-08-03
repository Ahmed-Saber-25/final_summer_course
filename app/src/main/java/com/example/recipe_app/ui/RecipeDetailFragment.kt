package com.example.recipe_app.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.recipe_app.databinding.FragmentRecipeDetailBinding
import com.example.recipe_app.ui.RecipeDetailFragmentArgs

class RecipeDetailFragment : Fragment() {
    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recipe = RecipeDetailFragmentArgs.fromBundle(requireArguments()).selectedRecipe
        binding.apply {
            tvRecipeTitle.text = recipe.strMeal
            tvInstructions.text = recipe.strInstructions
            Glide.with(requireContext()).load(recipe.strMealThumb).into(ivRecipeImage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
