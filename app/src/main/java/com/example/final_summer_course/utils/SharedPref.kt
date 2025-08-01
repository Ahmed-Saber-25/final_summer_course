package com.example.final_summer_course.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson

class SharedPref private constructor(private val sharedPreferences: SharedPreferences) {

    // Companion object to manage the singleton instance
    companion object {
        @Volatile
        private var INSTANCE: SharedPref? = null

        // Initialize the SharedPref singleton with the ApplicationContext
        fun init(context: Context) {
            synchronized(this) {
                if (INSTANCE == null) {
                    val prefs = context.applicationContext.getSharedPreferences(
                        "user_data",
                        Context.MODE_PRIVATE
                    )
                    INSTANCE = SharedPref(prefs)
                }
            }
        }

        // Get the singleton instance
        fun getInstance(): SharedPref {
            return INSTANCE
                ?: throw IllegalStateException("SharedPref not initialized. Call SharedPref.init(context) in your Application class.")
        }
    }

    fun saveData(
        key: String?,
        value: Any
    ) {
        sharedPreferences.edit {
            when (value) {
                is Int -> {
                    putInt(key, value)
                }

                is String -> {
                    putString(key, value)
                }

                is Boolean -> {
                    putBoolean(key, value)
                }

                is Long -> {
                    putLong(key, value)
                }

                is Float -> {
                    putFloat(key, value)
                }

                is Double -> {
                    putLong(key, java.lang.Double.doubleToRawLongBits(value))
                }
            }
        }
    }

    fun getData(
        key: String?,
        defaultValue: Any
    ): Any? {
        try {
            when (defaultValue) {
                is Int -> {
                    return sharedPreferences.getInt(key, defaultValue)
                }

                is String -> {
                    return sharedPreferences.getString(key, defaultValue)
                }

                is Boolean -> {
                    return sharedPreferences.getBoolean(key, defaultValue)
                }

                is Long -> {
                    return sharedPreferences.getLong(key, defaultValue)
                }

                is Float -> {
                    return sharedPreferences.getFloat(key, defaultValue)
                }
                // Handle Double retrieval if you save it as Long
                is Double -> {
                    return getDouble(key, defaultValue)
                }
            }
        } catch (e: Exception) {
            return defaultValue
        }
        return defaultValue
    }

    fun removeFromPref(key: String?) {
        sharedPreferences.edit {
            remove(key)
        }
    }

    fun getDouble(key: String?, defaultValue: Double): Double = java.lang.Double.longBitsToDouble(
        sharedPreferences.getLong(
            key,
            java.lang.Double.doubleToRawLongBits(defaultValue)
        )
    )

    fun getString(key: String, defaultValue: String): String =
        sharedPreferences.getString(key, defaultValue)!!

    fun getInt(key: String, defaultValue: Int): Int =
        sharedPreferences.getInt(key, defaultValue)

    fun saveUser(value: User) {
        sharedPreferences.edit {
            val gson = Gson()
            val json = gson.toJson(value)
            putString("user", json)
        }
    }

    fun getUser(): User? {
        val json = sharedPreferences.getString("user", null)
        return if (json.isNullOrEmpty()) null else Gson().fromJson(json, User::class.java)
    }
}
