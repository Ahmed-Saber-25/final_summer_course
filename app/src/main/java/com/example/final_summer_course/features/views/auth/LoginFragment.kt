package com.example.final_summer_course.features.views.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.final_summer_course.databinding.FragmentLoginBinding
import com.example.final_summer_course.features.views.recipe.RecipeActivity
import com.example.final_summer_course.utils.SharedPref
import com.example.final_summer_course.utils.User


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val email = binding.loginEmail.text.toString().trim()
            val password = binding.loginPassword.text.toString().trim()
            val savedUser: User? = SharedPref.getInstance().getUser()

            checkValidation(email, password, savedUser)

        }


        binding.registerText.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }
    }

    fun checkValidation(email: String, password: String, savedUser: User?) {
        binding.errorEmail.visibility = View.GONE
        binding.errorPassword.visibility = View.GONE
        if (email.isEmpty()) {
            binding.errorEmail.text = "Email is required"
            binding.errorEmail.visibility = View.VISIBLE
        } else if (password.isEmpty()) {
            binding.errorPassword.text = "Password is required"
            binding.errorPassword.visibility = View.VISIBLE
        } else if (savedUser != null && savedUser.email == email && savedUser.password == password) {
            binding.errorEmail.visibility = View.GONE
            binding.errorPassword.visibility = View.GONE
            SharedPref.getInstance().saveData("isLoggedIn", true)
            val intent = Intent(requireContext(), RecipeActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        } else {
            binding.errorEmail.visibility = View.GONE
            binding.errorPassword.visibility = View.VISIBLE
            binding.errorPassword.text = "Please check your Email/Password"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
