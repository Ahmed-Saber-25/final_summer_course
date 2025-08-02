package com.example.final_summer_course.features.views.recipe

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_summer_course.R
import com.example.final_summer_course.databinding.FragmentRecipeListBinding
import com.example.final_summer_course.features.views.auth.AuthActivity
import com.example.final_summer_course.features.views.auth.LoginFragment
import com.example.final_summer_course.features.views.recipe.api.RetrofitHelper
import com.example.final_summer_course.utils.SharedPref
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeListFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {

    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        val savedUser = SharedPref.getInstance().getUser()
        val headerView = binding.navigationView.getHeaderView(0)
        val titleView = headerView.findViewById<TextView>(R.id.drawer_header_title)
        val subtitleView = headerView.findViewById<TextView>(R.id.drawer_header_subtitle)
        titleView.text = "Welcome ${savedUser?.name}"
        subtitleView.text = "${savedUser?.email}"

        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(Color.WHITE)

        toggle = ActionBarDrawerToggle(
            activity,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.drawerArrowDrawable.color = Color.WHITE
        toggle.syncState()



        binding.navigationView.setNavigationItemSelectedListener(this)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val productResponse = RetrofitHelper.instance.listByFirstLetter("a")
                val productsList = productResponse.meals

                withContext(Dispatchers.Main) {
                    binding.recyclerView.adapter = MealAdapter(productsList)
                    binding.progressBar.visibility = View.GONE
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.home ->  navController.navigate(R.id.recipeListFragment)
            R.id.search -> Toast.makeText(context, "Search clicked", Toast.LENGTH_SHORT).show()
            R.id.favorite -> Toast.makeText(context, "Favorite clicked", Toast.LENGTH_SHORT)
                .show()

            R.id.logout -> {
                SharedPref.getInstance().saveData("isLoggedIn", false)
                val intent = Intent(requireContext(), AuthActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
                Toast.makeText(context, "Logout Successfully", Toast.LENGTH_SHORT).show()
            }

            R.id.delete_account -> {
                SharedPref.getInstance().deleteUser()
                val intent = Intent(requireContext(), AuthActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
                Toast.makeText(context, "Account Deleted Successfully", Toast.LENGTH_SHORT).show()
            }

            R.id.about -> Toast.makeText(context, "About clicked", Toast.LENGTH_SHORT).show()
        }
        binding.drawerLayout.closeDrawers()
        return true
    }

}
