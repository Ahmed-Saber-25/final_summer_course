package com.example.final_summer_course.features.views.recipe

import com.example.final_summer_course.features.views.recipe.models.MealModel
import androidx.navigation.fragment.navArgs
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.final_summer_course.R
import com.example.final_summer_course.databinding.FragmentRecipeItemDetailsBinding
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.final_summer_course.features.views.recipe.database.MealDatabase
import kotlinx.coroutines.launch

class RecipeItemDetailsFragment : Fragment() {
    private var _binding: FragmentRecipeItemDetailsBinding? = null
    private val binding get() = _binding!!

    private var meal: MealModel? = null
    private val args: RecipeItemDetailsFragmentArgs by navArgs()
    private lateinit var mealViewModel: MealViewModel
    private var isFavorite: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeItemDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        meal = args.meal
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.item_details_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val favItem = menu.findItem(R.id.add_favorite)

        meal?.let { mealItem ->
            lifecycleScope.launch {
                isFavorite = mealViewModel.isMealFavorite(mealItem.id)
                favItem.icon = ContextCompat.getDrawable(
                    requireContext(),
                    if (isFavorite) R.drawable.favorite else R.drawable.favorite_empty
                )
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMealViewModel()

        setupToolbar()

        meal?.let { meal ->
            binding.textViewTitle.text = meal.title

            binding.textViewCategory.text = "${meal.category} • ${meal.area}"

            Glide.with(this)
                .load(meal.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.person)
                .into(binding.imageViewMeal)

            binding.textViewIngredients.text = getIngredientsList(meal)

            binding.textViewInstructions.text = meal.instructions ?: "No instructions available"
        }
    }

    private fun setupToolbar() {
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)

        binding.toolbar.title = meal?.title ?: "Recipe Details"
        binding.toolbar.setTitleTextColor(Color.WHITE)
        binding.toolbar.overflowIcon?.setTint(
            ContextCompat.getColor(
                requireContext(),
                android.R.color.white
            )
        )
        binding.toolbar.setNavigationIcon(R.drawable.outline_arrow_back_ios_24)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    fun setupMealViewModel() {
        val dao = MealDatabase.getInstance(requireContext()).mealDao()
        val repo = MealRepositoryImpl(dao)
        val factory = MealViewModelFactory(repo)
        mealViewModel = viewModels<MealViewModel> { factory }.value
    }

    private fun getIngredientsList(meal: MealModel): String {
        val ingredients = listOf(
            meal.ingredient1 to meal.measure1,
            meal.ingredient2 to meal.measure2,
            meal.ingredient3 to meal.measure3,
            meal.ingredient4 to meal.measure4,
            meal.ingredient5 to meal.measure5,
            meal.ingredient6 to meal.measure6,
            meal.ingredient7 to meal.measure7,
            meal.ingredient8 to meal.measure8,
            meal.ingredient9 to meal.measure9,
            meal.ingredient10 to meal.measure10,
            meal.ingredient11 to meal.measure11,
            meal.ingredient12 to meal.measure12,
            meal.ingredient13 to meal.measure13,
            meal.ingredient14 to meal.measure14,
            meal.ingredient15 to meal.measure15,
            meal.ingredient16 to meal.measure16,
            meal.ingredient17 to meal.measure17,
            meal.ingredient18 to meal.measure18,
            meal.ingredient19 to meal.measure19,
            meal.ingredient20 to meal.measure20,
        )

        var result: String = ""

        for (i in ingredients.indices) {
            val ingredient = ingredients[i].first
            val measure = ingredients[i].second

            if (!ingredient.isNullOrBlank()) {
                result += "• $ingredient"
                if (!measure.isNullOrBlank()) {
                    result += " — $measure"
                }
                result += "\n"
            }
        }

        return result.trim()
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.youtube_item -> {
                meal?.youtubeUrl?.let { url ->
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    startActivity(intent)
                }
                true
            }

            R.id.add_favorite -> {
                meal?.let { meal ->
                    lifecycleScope.launch {
                        val isFavorite = mealViewModel.isMealFavorite(meal.id)

                        item.icon = ContextCompat.getDrawable(
                            requireContext(),
                            if (!isFavorite) R.drawable.favorite else R.drawable.favorite_empty
                        )

                        if (!isFavorite) {
                            Toast.makeText(
                                requireContext(),
                                "Added to favorites",
                                Toast.LENGTH_SHORT
                            ).show()
                            mealViewModel.saveMeal(meal)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Removed from favorites",
                                Toast.LENGTH_SHORT
                            ).show()
                            mealViewModel.deleteMeal(meal)
                        }
                    }
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}