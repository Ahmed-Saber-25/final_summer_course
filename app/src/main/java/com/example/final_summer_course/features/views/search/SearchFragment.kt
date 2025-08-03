package com.example.final_summer_course.features.views.search

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_summer_course.databinding.FragmentSearchBinding
import com.example.final_summer_course.features.views.recipe.MealAdapter
import com.example.final_summer_course.features.views.recipe.RecipeListFragmentDirections
import com.example.final_summer_course.features.views.recipe.api.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    private var searchJob: Job? = null

    private lateinit var mealAdapter: MealAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.toolbar.setTitleTextColor(Color.WHITE)
        binding.progressBar.visibility = View.GONE
        initMealsAdapter()
        onEditTextChanged()
    }

    private fun initMealsAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mealAdapter = MealAdapter { meal ->
            val action = SearchFragmentDirections
                .actionSearchFragmentToRecipeItemDetailsFragment(meal)
            navController.navigate(action)
        }
        binding.recyclerView.adapter = mealAdapter
    }

    fun onEditTextChanged() {
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {
                val recipe = text.toString().trim()
                if (recipe.isNotEmpty()) {
                    searchJob?.cancel()
                    searchJob = lifecycleScope.launch {
                        delay(300)
                        binding.progressBar.visibility = View.VISIBLE
                        searchMeals(recipe)
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private suspend fun searchMeals(query: String) {
        try {
            val mealsResponse = withContext(Dispatchers.IO) {
                RetrofitHelper.instance.searchMealByName(query)
            }
            val meals = mealsResponse.meals
            if (meals.isNotEmpty()) {
                binding.recyclerView.visibility = View.VISIBLE
                mealAdapter.submitList(meals)
            } else {
                binding.recyclerView.visibility = View.GONE
                Toast.makeText(requireContext(), "No meals found", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.d("SearchFragment", "Error: ${e.message}")
        } finally {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        searchJob?.cancel()
    }
}
