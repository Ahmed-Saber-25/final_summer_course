package com.example.recipe_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe_app.data.model.FavoriteRecipe
import com.example.recipe_app.databinding.ItemRecipeBinding

class FavoritesAdapter(
    private var favoritesList: List<FavoriteRecipe> = listOf(),
    private val onItemClick: (FavoriteRecipe) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    inner class FavoritesViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: FavoriteRecipe) {
            binding.tvTitle.text = recipe.strMeal
            Glide.with(binding.root.context)
                .load(recipe.strMealThumb)
                .into(binding.ivMeal)

            binding.root.setOnClickListener {
                onItemClick(recipe)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding = ItemRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(favoritesList[position])
    }

    override fun getItemCount(): Int = favoritesList.size

    fun updateData(newList: List<FavoriteRecipe>) {
        favoritesList = newList
        notifyDataSetChanged()
    }
}
// attemp2@example.com
// 555555