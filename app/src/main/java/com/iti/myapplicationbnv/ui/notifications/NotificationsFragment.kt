package com.iti.myapplicationbnv.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.iti.myapplicationbnv.databinding.FragmentNotificationsBinding // Assuming this binding is correct
import com.google.android.material.textfield.TextInputEditText // Import TextInputEditText
import com.google.android.material.textfield.TextInputLayout // Import TextInputLayout

class NotificationsFragment : Fragment() {
    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var usernameInputLayout: TextInputLayout
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var phoneInputLayout: TextInputLayout
    private lateinit var phoneEditText: TextInputEditText

    private lateinit var validateButton: Button
    private lateinit var clearErrorsButton: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize views from the inflated binding
        emailInputLayout = binding.emailInputLayout
        emailEditText = binding.emailEditText
        passwordInputLayout = binding.passwordInputLayout
        passwordEditText = binding.passwordEditText
        usernameInputLayout = binding.usernameInputLayout
        usernameEditText = binding.usernameEditText
        phoneInputLayout = binding.phoneInputLayout
        phoneEditText = binding.phoneEditText

        validateButton = binding.validateButton
        clearErrorsButton = binding.clearErrorsButton

        // Set up button click listeners
        validateButton.setOnClickListener {
            validateInputs()
        }

        clearErrorsButton.setOnClickListener {
            showSnackbarWithAction("This is a Snackbar message!", "UNDO") {
                // Action to perform when UNDO is clicked
                Toast.makeText(requireContext(), "UNDO action clicked!", Toast.LENGTH_SHORT).show()
            }
            clearAllErrors()
        }

        return root
    }
    private fun showSnackbarWithAction(message: String, actionText: String? = null, actionListener: (() -> Unit)? = null) {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
        if (actionText != null && actionListener != null) {
            snackbar.setAction(actionText) {
                actionListener.invoke()
            }
        }
        snackbar.show()
    }

    private fun validateInputs() {
        clearAllErrors()
        var isValid = true
        val email = emailEditText.text.toString().trim()
        if (email.isEmpty()) {
            emailInputLayout.error = "Email cannot be empty"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.error = "Invalid email format"
            isValid = false
        }
        val password = passwordEditText.text.toString()
        if (password.isEmpty()) {
            passwordInputLayout.error = "Password cannot be empty"
            isValid = false
        } else if (password.length < 6) {
            passwordInputLayout.error = "Password must be at least 6 characters"
            isValid = false
        }
        val username = usernameEditText.text.toString().trim()
        if (username.isEmpty()) {
            usernameInputLayout.error = "Username cannot be empty"
            isValid = false
        } else if (username.length < 3) {
            usernameInputLayout.error = "Username must be at least 3 characters"
            isValid = false
        }
        val phone = phoneEditText.text.toString().trim()
        if (phone.isEmpty()) {
            phoneInputLayout.error = "Phone number cannot be empty"
            isValid = false
        } else if (phone.length < 7) { // Very basic length check
            phoneInputLayout.error = "Invalid phone number"
            isValid = false
        }

        if (isValid) {
            Toast.makeText(requireContext(), "All inputs are valid!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Please fix the errors.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearAllErrors() {
        emailInputLayout.error = null
        passwordInputLayout.error = null
        usernameInputLayout.error = null
        phoneInputLayout.error = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
