package com.example.final_summer_course

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.final_summer_course.databinding.FragmentSplashBinding
import com.example.final_summer_course.features.views.auth.AuthActivity
import com.example.final_summer_course.features.views.recipe.RecipeActivity
import com.example.final_summer_course.utils.SharedPref

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({

            val isLoggedIn = SharedPref.getInstance().getData("isLoggedIn", false) as Boolean

//            SharedPref.getInstance().saveData("isLoggedIn", false)

            if (isLoggedIn) {
                val intent = Intent(requireContext(), RecipeActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            } else {
                val intent = Intent(requireContext(), AuthActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }

        }, 3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
