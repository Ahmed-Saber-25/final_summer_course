package com.example.recipe_app.data.local

import android.content.Context

class SharedPrefrence(context: Context) {
    private val sharedPref = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)

    fun saveUser(email: String, password: String) {
        sharedPref.edit().apply {
            putString("email", email)
            putString("password", password)
            putBoolean("isLoggedIn", true)
            apply()
        }
    }

    fun getEmail(): String? {
        return sharedPref.getString("email", null)
    }



    fun isLoggedIn(): Boolean = sharedPref.getBoolean("isLoggedIn", false)

    fun checkLogin(email: String, password: String): Boolean {
        val savedEmail = sharedPref.getString("email", "")
        val savedPassword = sharedPref.getString("password", "")
        return email == savedEmail && password == savedPassword
    }

    fun logout() {
        sharedPref.edit().clear().apply()
    }
}
