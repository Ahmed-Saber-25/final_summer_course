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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.recipe_app.R
import com.example.recipe_app.RecipeActivity
import com.example.recipe_app.data.local.SharedPrefrence
import com.example.recipe_app.data.model.RecipeDatabase
import com.example.recipe_app.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFragment : Fragment() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var sharedPref: SharedPrefrence

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPrefrence(requireContext())

        emailEditText = view.findViewById(R.id.email_input)
        passwordEditText = view.findViewById(R.id.password_input)
        confirmPasswordEditText = view.findViewById(R.id.confirm_password_input)
        registerButton = view.findViewById(R.id.register_button)

        val loginTextView = view.findViewById<TextView>(R.id.textView2)
        loginTextView.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
        }

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(email = email, password = password)
            val db = RecipeDatabase.getDatabase(requireContext())

            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    db.userDao().insertUser(user)

                    // Save user credentials and login status
                    sharedPref.saveUser(email, password)

                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Registered successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(requireActivity(), RecipeActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Registration failed: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
