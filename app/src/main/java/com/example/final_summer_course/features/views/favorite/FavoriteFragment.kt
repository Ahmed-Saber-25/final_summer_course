package com.example.final_summer_course.features.views.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_summer_course.databinding.FragmentFavoriteBinding
import com.example.final_summer_course.features.views.recipe.MealAdapter
import com.example.final_summer_course.features.views.recipe.MealRepository
import com.example.final_summer_course.features.views.recipe.MealRepositoryImpl
import com.example.final_summer_course.features.views.recipe.MealViewModel
import com.example.final_summer_course.features.views.recipe.MealViewModelFactory
import com.example.final_summer_course.features.views.recipe.database.MealDatabase

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val mealRepository: MealRepository by lazy {
        val mealDao = MealDatabase.Companion.getInstance(requireContext()).mealDao()
        MealRepositoryImpl(mealDao)
    }

    private val mealViewModel: MealViewModel by viewModels {
        MealViewModelFactory(mealRepository)
    }

    private lateinit var mealAdapter: MealAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealAdapter = MealAdapter(emptyList()) { clickedMeal ->
            val action = FavoriteFragmentDirections
                .actionFavoriteFragmentToRecipeItemDetailsFragment(clickedMeal)
            findNavController().navigate(action)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mealAdapter
        }
        mealViewModel.allMeals.observe(viewLifecycleOwner, Observer { meals ->
            if (meals.isNullOrEmpty()) {
                binding.recyclerView.visibility = View.GONE
                Toast.makeText(requireContext(), "No favorite meals saved yet.", Toast.LENGTH_LONG)
                    .show()
                mealAdapter.updateData(emptyList())
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                mealAdapter.updateData(meals)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mealViewModel.allMeals.observe(viewLifecycleOwner) { meals ->
            if (meals.isNullOrEmpty()) {
                binding.recyclerView.visibility = View.GONE
                mealAdapter.updateData(emptyList())
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                mealAdapter.updateData(meals)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}