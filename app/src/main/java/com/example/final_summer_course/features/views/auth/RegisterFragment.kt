package com.example.final_summer_course.features.views.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.final_summer_course.databinding.FragmentRegisterBinding
import com.example.final_summer_course.features.views.recipe.RecipeActivity
import com.example.final_summer_course.utils.SharedPref
import com.example.final_summer_course.utils.User

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            val name = binding.registerName.text.toString().trim()
            val email = binding.registerEmail.text.toString().trim()
            val password = binding.registerPassword.text.toString().trim()

            checkValidation(name, email, password)
        }

        binding.loginTitle.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }

    fun checkValidation(name: String, email: String, password: String) {
        binding.errorEmail.visibility = View.GONE
        binding.errorPassword.visibility = View.GONE
        binding.errorName.visibility = View.GONE
        if (name.isEmpty()) {
            binding.errorName.text = "Name is required"
            binding.errorName.visibility = View.VISIBLE
        } else if (name.length < 3) {
            binding.errorName.text = "Name must be at least 3 characters"
            binding.errorName.visibility = View.VISIBLE
        } else if (email.isEmpty()) {
            binding.errorEmail.text = "Email is required"
            binding.errorEmail.visibility = View.VISIBLE
        } else if (password.isEmpty()) {
            binding.errorPassword.text = "Password is required"
            binding.errorPassword.visibility = View.VISIBLE
        } else if (password.length < 6) {
            binding.errorPassword.text = "Password must be at least 6 characters"
            binding.errorPassword.visibility = View.VISIBLE
        } else {
            binding.errorEmail.visibility = View.GONE
            binding.errorPassword.visibility = View.GONE
            binding.errorName.visibility = View.GONE
            SharedPref.getInstance().saveData("isLoggedIn", true)

            SharedPref.getInstance().saveUser(User(name, email, password))

            val intent = Intent(requireContext(), RecipeActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
