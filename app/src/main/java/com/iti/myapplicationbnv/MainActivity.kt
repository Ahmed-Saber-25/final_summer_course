package com.iti.myapplicationbnv

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.iti.myapplicationbnv.databinding.ActivityMainBinding
import com.iti.myapplicationbnv.utils.SharedPref
import kotlinx.serialization.Serializable

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        SharedPref.init(context = this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, // Bottom Nav destinations
                R.id.nav_home_drawer, R.id.nav_gallery_drawer, R.id.nav_slideshow_drawer,R.id.nav_share_drawer,R.id.nav_send_drawer // Drawer Nav destinations
            )
            , binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
//         Handle clicks on NavigationView items (optional, if you need custom logic)
        binding.navDrawerView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home_drawer -> {
                    Toast.makeText(this, "Drawer Home clicked!", Toast.LENGTH_SHORT).show()
                    // navController.navigate(R.id.navigation_home) // Example: navigate to bottom nav home
                    true
                }
                R.id.nav_gallery_drawer -> {
                    Toast.makeText(this, "Drawer Gallery clicked!", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_slideshow_drawer -> {
                    Toast.makeText(this, "Drawer SlideShow clicked!", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_share_drawer -> {
                    Toast.makeText(this, "share clicked!", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_send_drawer -> {
                    Toast.makeText(this, "send clicked!", Toast.LENGTH_SHORT).show()
                    true
                }
                // Add more cases for other drawer items
                else -> false
            }
            binding.drawerLayout.closeDrawer(binding.navDrawerView) // Close the drawer after item click
            true // Indicate that the item click was handled
        }
    }
    /**
     * Inflates the options menu for the AppBar.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_menu, menu) // Inflate your custom menu
        return true
    }

    /**
     * Handles clicks on items in the options menu.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, "Settings clicked!", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_about -> {
                Toast.makeText(this, "About clicked!", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_logout -> {
                Toast.makeText(this, "Logout clicked!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

//    /**
//     * Called when the user presses the Up button in the action bar.
//     * This handles opening/closing the drawer or navigating up in the hierarchy.
//     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
@Serializable
data class User(val name: String, val title: String):java.io.Serializable
