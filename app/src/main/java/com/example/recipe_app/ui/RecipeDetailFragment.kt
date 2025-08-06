package com.example.recipe_app.ui

import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.recipe_app.R
import com.example.recipe_app.data.model.Recipe
import com.example.recipe_app.databinding.FragmentRecipeDetailBinding
import com.example.recipe_app.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

class RecipeDetailFragment : Fragment() {
    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipeViewModel by viewModels()
    private var isFavorited = false

    private lateinit var currentRecipe: Recipe

    private var isExpanded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentRecipe = RecipeDetailFragmentArgs.fromBundle(requireArguments()).selectedRecipe

        binding.apply {
            // Set title and image
            detailTitle.text = currentRecipe.strMeal
            Glide.with(requireContext()).load(currentRecipe.strMealThumb).into(detailImage)

            // Show collapsed instructions first
            detailinstructions.text = currentRecipe.strInstructions
            detailinstructions.maxLines = 3
            detailinstructions.ellipsize = TextUtils.TruncateAt.END

            // Expand/collapse toggle
            expandButton.setOnClickListener {
                if (isExpanded) {
                    detailinstructions.maxLines = 3
                    detailinstructions.ellipsize = TextUtils.TruncateAt.END
                    expandButton.text = getString(R.string.show_full_recipe)
                } else {
                    detailinstructions.maxLines = Int.MAX_VALUE
                    detailinstructions.ellipsize = null
                    expandButton.text = getString(R.string.hide_full_recipe)
                }
                isExpanded = !isExpanded
            }

            // Watch Video button logic
            videoButton.setOnClickListener {
                currentRecipe.strYoutube?.let { youtubeUrl ->
                    if (youtubeUrl.isNotBlank()) {
                        val videoId = Uri.parse(youtubeUrl).getQueryParameter("v")
                        if (videoId != null) {
                            val embedUrl = "https://www.youtube.com/embed/$videoId"

                            // Make overlay visible
                            videoOverlay.visibility = View.VISIBLE

                            // Configure WebView
                            val webView = webViewPlayer
                            webView.settings.javaScriptEnabled = true
                            webView.webViewClient = WebViewClient()
                            webView.loadUrl(embedUrl)
                            webView.visibility = View.VISIBLE
                        } else {
                            Toast.makeText(requireContext(), "Invalid YouTube URL", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "No video available", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            // Close Video button logic
            closeOverlayButton.setOnClickListener {
                // Hide overlay and stop playback
                videoOverlay.visibility = View.GONE
                webViewPlayer.loadUrl("about:blank")  // stops video
                webViewPlayer.visibility = View.GONE
            }

            // Setup favorite button initial state asynchronously
            lifecycleScope.launch {
                isFavorited = viewModel.isFavorite(currentRecipe)
                updateFavoriteButtonIcon(isFavorited)
            }

            // Favorite button click listener
            favoriteButton.setOnClickListener {
                lifecycleScope.launch {
                    if (isFavorited) {
                        viewModel.removeFromFavorites(currentRecipe)
                        isFavorited = false
                    } else {
                        viewModel.addToFavorites(currentRecipe)
                        isFavorited = true
                    }
                    updateFavoriteButtonIcon(isFavorited)
                }
            }
        }
    }

    private fun updateFavoriteButtonIcon(isFav: Boolean) {
        val iconRes = if (isFav) R.drawable.ic_favorite_border else R.drawable.ic_favorite_border_empty
        binding.favoriteButton.setImageResource(iconRes)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
