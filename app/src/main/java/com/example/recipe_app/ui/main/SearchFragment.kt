package com.example.recipe_app.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe_app.adapter.HomeAdapter
import com.example.recipe_app.databinding.FragmentSearchBinding
import com.example.recipe_app.data.model.Recipe
import com.example.recipe_app.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeAdapter: HomeAdapter
    private val searchResults = mutableListOf<Recipe>()

    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeAdapter = HomeAdapter(searchResults) { selectedRecipe ->
            val action = SearchFragmentDirections.actionSearchFragmentToRecipeDetailFragment(selectedRecipe)
            findNavController().navigate(action)
        }

        binding.searchRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeAdapter
        }

        binding.searchEditText.addTextChangedListener { text ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500) // debounce delay
                text?.let {
                    if (it.length >= 2) {
                        searchRecipes(it.toString())
                    } else {
                        clearResults()
                    }
                }
            }
        }
    }

    private fun searchRecipes(query: String) {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitInstance.api.getMeals(query)
                }
                response.meals?.let {
                    searchResults.clear()
                    searchResults.addAll(it)
                    homeAdapter.notifyDataSetChanged()
                } ?: clearResults()
            } catch (e: Exception) {
                showToast("Search failed: ${e.localizedMessage}")
            }
        }
    }


    private fun clearResults() {
        searchResults.clear()
        homeAdapter.notifyDataSetChanged()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
