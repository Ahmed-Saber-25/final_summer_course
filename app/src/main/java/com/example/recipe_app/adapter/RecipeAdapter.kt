package com.example.recipe_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe_app.R
import com.example.recipe_app.data.model.Recipe

class RecipeAdapter(
    private val recipes: List<Recipe>,
    private val onItemClick: (Recipe) -> Unit,
    private val onFavoriteClick: (Recipe, Boolean) -> Unit,
    private val isFavorite: (Recipe) -> Boolean
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeTitle: TextView = view.findViewById(R.id.tvTitle)
        val recipeImage: ImageView = view.findViewById(R.id.ivMeal)
        val heartIcon: ImageView = view.findViewById(R.id.ivFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]

        holder.recipeTitle.text = recipe.strMeal
        Glide.with(holder.itemView.context).load(recipe.strMealThumb).into(holder.recipeImage)

        // Set heart icon based on favorite status
        val isFav = isFavorite(recipe)
        holder.heartIcon.setImageResource(
            if (isFav) R.drawable.ic_favorite_border else R.drawable.ic_favorite_border_empty
        )

        // Click on item
        holder.itemView.setOnClickListener {
            onItemClick(recipe)
        }

        // Click on heart icon
        holder.heartIcon.setOnClickListener {
            val currentlyFav = isFavorite(recipe)
            val newFavState = !currentlyFav
            onFavoriteClick(recipe, newFavState)
            notifyItemChanged(position)
        }
    }

    override fun getItemCount() = recipes.size
}
