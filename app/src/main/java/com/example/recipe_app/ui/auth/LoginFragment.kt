package com.example.recipe_app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.recipe_app.R
import com.example.recipe_app.RecipeActivity
import com.example.recipe_app.data.local.SharedPrefrence // Import your SharedPrefrence class

class LoginFragment : Fragment() {

    private lateinit var sharedPref: SharedPrefrence

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPrefrence(requireContext())

        val emailEditText = view.findViewById<EditText>(R.id.email_input)
        val passwordEditText = view.findViewById<EditText>(R.id.password_input)
        val loginButton = view.findViewById<Button>(R.id.login_button)
        val signUpTextView = view.findViewById<TextView>(R.id.textView2)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Check if credentials match stored ones
            if (sharedPref.checkLogin(email, password)) {
                sharedPref.saveUser(email, password) // Mark user as logged in
                val intent = Intent(requireActivity(), RecipeActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }

        signUpTextView.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}
