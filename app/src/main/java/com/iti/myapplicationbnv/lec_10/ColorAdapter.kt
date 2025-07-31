package com.iti.myapplicationbnv.lec_10

import android.graphics.Color as AndroidColor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iti.myapplicationbnv.R

class ColorAdapter(private val onClick: (Color) -> Unit) : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    private var colors: List<Color> = emptyList()

    fun submitList(newColors: List<Color>) {
        colors = newColors
        notifyDataSetChanged() // For simplicity, in a real app use DiffUtil
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val currentColor = colors[position]
        holder.bind(currentColor)
        holder.itemView.setOnClickListener {
            onClick(currentColor)
        }
    }

    override fun getItemCount(): Int = colors.size

    inner class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val colorView: View = itemView.findViewById(R.id.colorView)
        private val colorNameTextView: TextView = itemView.findViewById(R.id.textViewColorName)
        private val colorHexTextView: TextView = itemView.findViewById(R.id.textViewColorHex)

        fun bind(color: Color) {
            colorNameTextView.text = color.name
            colorHexTextView.text = color.hex

            try {
                // Set the background color from the hex string
                colorView.setBackgroundColor(AndroidColor.parseColor(color.hex))
            } catch (e: IllegalArgumentException) {
                // Handle invalid hex code, set a default color
                colorView.setBackgroundColor(AndroidColor.GRAY)
                colorHexTextView.text = "Invalid Hex: ${color.hex}"
                e.printStackTrace()
            }
        }
    }
}