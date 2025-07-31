package com.iti.myapplicationbnv.lec_10

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.iti.myapplicationbnv.databinding.ActivityColorDetailBinding
import android.graphics.Color as AndroidColor

class ColorDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityColorDetailBinding
    private lateinit var colorViewModel: ColorViewModel

    private var currentColor: Color? = null

    companion object {
        const val EXTRA_COLOR_ID = "extra_color_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityColorDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "ColorValue"
        // ViewModel setup
        val colorDao = ColorDatabase.getInstance(applicationContext).colorDao()
        val repository = ColorRepositoryImpl(colorDao)
        val factory = ColorViewModelFactory(repository)
        colorViewModel = viewModels<ColorViewModel> { factory }.value

        val colorId = intent.getIntExtra(EXTRA_COLOR_ID, -1)
        if (colorId == -1) {
            Toast.makeText(this, "Error: Color ID not provided", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        colorViewModel.allColors.observe(this) { colors ->
            val foundColor = colors?.find { it._id == colorId }
            if (foundColor != null) {
                currentColor = foundColor // Store the current color
                binding.detailColorName.text = foundColor.name
                binding.detailColorHex.text = foundColor.hex
                try {
                    binding.detailColorView.setBackgroundColor(AndroidColor.parseColor(foundColor.hex))
                } catch (e: IllegalArgumentException) {
                    binding.detailColorView.setBackgroundColor(AndroidColor.GRAY)
                }
            } else {
                Toast.makeText(this, "Color not found", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        colorViewModel.fetchAllColors()

        binding.buttonDeleteColor.setOnClickListener {
            currentColor?.let { colorToDelete ->
                colorViewModel.deleteColor(colorToDelete)
                Toast.makeText(this, "${colorToDelete.name} deleted", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}