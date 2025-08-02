package com.example.final_summer_course.features.views.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.final_summer_course.R
import com.example.final_summer_course.features.views.recipe.models.MealModel

class MealAdapter(
    private val meals: List<MealModel>,
    private val onItemClick: (MealModel) -> Unit
) : RecyclerView.Adapter<MealAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.meal_image)
        val title: TextView = view.findViewById(R.id.meal_title)
        val category: TextView = view.findViewById(R.id.meal_category)
        val area: TextView = view.findViewById(R.id.meal_area)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.meal_item, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = meals[position]
        holder.title.text = meal.title
        holder.category.text = "Category: ${meal.category}"
        holder.area.text = "Area: ${meal.area}"

        Glide.with(holder.image)
            .load(meal.imageUrl)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            onItemClick(meal)
        }
    }

    override fun getItemCount(): Int = meals.size
}

