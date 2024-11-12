package com.unitedtractors.elomate.data.local.user

import android.content.Context
import android.content.SharedPreferences

class UserPreference(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("YourPreferenceName", Context.MODE_PRIVATE)

    companion object {
        private const val AUTH_TOKEN_KEY = "authToken"
    }

    // Save auth token
    fun saveAuthToken(token: String) {
        preferences.edit().putString(AUTH_TOKEN_KEY, token).apply()
    }

    // Get auth token
    fun getAuthToken(): String? = preferences.getString(AUTH_TOKEN_KEY, null)

    // Clear auth token
    fun clearAuthToken() {
        preferences.edit().remove(AUTH_TOKEN_KEY).apply()
    }
}
