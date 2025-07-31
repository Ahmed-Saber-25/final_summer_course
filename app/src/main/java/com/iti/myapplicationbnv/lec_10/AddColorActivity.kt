package com.iti.myapplicationbnv.lec_10
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.iti.myapplicationbnv.R
import com.iti.myapplicationbnv.databinding.ActivityAddColorBinding
import android.graphics.Color as AndroidColor

class AddColorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddColorBinding
    private lateinit var colorViewModel: ColorViewModel
    private var currentRed: Int = 0
    private var currentGreen: Int = 0
    private var currentBlue: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddColorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add New Card"
        // ViewModel setup
        val colorDao = ColorDatabase.getInstance(applicationContext).colorDao()
        val repository = ColorRepositoryImpl(colorDao)
        val factory = ColorViewModelFactory(repository)
        colorViewModel = viewModels<ColorViewModel> { factory }.value

        currentRed = 50
        currentGreen = 152
        currentBlue = 79
        binding.seekBarRed.progress = currentRed
        binding.seekBarGreen.progress = currentGreen
        binding.seekBarBlue.progress = currentBlue

        updateColorPreview()

        val seekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when (seekBar?.id) {
                    R.id.seekBarRed -> currentRed = progress
                    R.id.seekBarGreen -> currentGreen = progress
                    R.id.seekBarBlue -> currentBlue = progress
                }
                updateColorPreview()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        }

        binding.seekBarRed.setOnSeekBarChangeListener(seekBarChangeListener)
        binding.seekBarGreen.setOnSeekBarChangeListener(seekBarChangeListener)
        binding.seekBarBlue.setOnSeekBarChangeListener(seekBarChangeListener)


        binding.buttonAddColor.setOnClickListener {
            val name = binding.editTextColorName.text.toString().trim()
            val hex = String.format("#%02X%02X%02X", currentRed, currentGreen, currentBlue) // Get current hex from sliders

            if (name.isNotEmpty()) {
                val newColor = Color(name = name, hex = hex)
                colorViewModel.saveColor(newColor)
                Toast.makeText(this, "Color added!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Please enter a color name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateColorPreview() {
        val androidColor = AndroidColor.rgb(currentRed, currentGreen, currentBlue)
        binding.previewColorView.setBackgroundColor(androidColor)
        val hexString = String.format("#%02X%02X%02X", currentRed, currentGreen, currentBlue)
        binding.textViewHexCodePreview.text = hexString
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}