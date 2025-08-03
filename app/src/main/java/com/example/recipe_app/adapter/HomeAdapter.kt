package com.example.recipe_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe_app.databinding.ItemRecipeBinding
import com.example.recipe_app.model.Recipe

class HomeAdapter(
    private val recipes: List<Recipe>,
    private val onItemClick: (Recipe) -> Unit
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            binding.tvTitle.text = recipe.name
            Glide.with(binding.ivMeal.context)
                .load(recipe.imageUrl)
                .into(binding.ivMeal)

            binding.root.setOnClickListener {
                onItemClick(recipe)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount() = recipes.size
}
