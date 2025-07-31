package com.iti.myapplicationbnv.lec_10

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.iti.myapplicationbnv.R
import com.iti.myapplicationbnv.databinding.ActivityColorListBinding

class ColorListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityColorListBinding

    private lateinit var colorViewModel: ColorViewModel
    private lateinit var colorAdapter: ColorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityColorListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "ColorValue"

        // 1. Get the Room Database instance and DAO
        val colorDao = ColorDatabase.getInstance(applicationContext).colorDao()

        // 2. Create the Repository instance
        val repository = ColorRepositoryImpl(colorDao)

        // 3. Create the ViewModelFactory with the repository and application context
        val factory = ColorViewModelFactory(repository)

        // 4. Get the ViewModel using the factory
        colorViewModel = viewModels<ColorViewModel> { factory }.value

        // Set up RecyclerView
        colorAdapter = ColorAdapter { color ->
            // Handle item click: navigate to ColorDetailActivity
            val intent = Intent(this, ColorDetailActivity::class.java).apply {
                putExtra(ColorDetailActivity.EXTRA_COLOR_ID, color._id)
            }
            startActivity(intent)
        }
        binding.recyclerViewColors.adapter = colorAdapter

        // Observe changes in the list of all colors
        colorViewModel.allColors.observe(this, Observer { colors ->
            colors?.let {
                colorAdapter.submitList(it)
                Log.d("ColorListActivity", "Colors updated: ${it.size} colors")
            }
        })

        // Set up FAB click listener to add new color
        binding.fabAddColor.setOnClickListener {
            val intent = Intent(this, AddColorActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.color_list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        return when (item.itemId) {
            R.id.action_create -> {
                val intent = Intent(this, AddColorActivity::class.java)
                startActivity(intent)
                true
            }
            // You can add more menu items here if needed
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh the list whenever the activity resumes (e.g., after adding/deleting a color)
        colorViewModel.fetchAllColors()
    }
}