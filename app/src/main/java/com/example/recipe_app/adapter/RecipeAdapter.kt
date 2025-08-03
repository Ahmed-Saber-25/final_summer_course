package com.example.recipe_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe_app.R
import com.example.recipe_app.model.Recipe

class RecipeAdapter(
    private val recipes: List<Recipe>,
    private val onClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeTitle: TextView = view.findViewById(R.id.recipeTitle)
        val recipeImage: ImageView = view.findViewById(R.id.recipeImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_card, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.recipeTitle.text = recipe.strMeal
        Glide.with(holder.itemView.context).load(recipe.strMealThumb).into(holder.recipeImage)
        holder.itemView.setOnClickListener { onClick(recipe) }
    }

    override fun getItemCount() = recipes.size
}
