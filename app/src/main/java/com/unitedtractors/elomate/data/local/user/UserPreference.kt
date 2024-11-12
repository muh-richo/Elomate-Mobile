package com.unitedtractors.elomate.data.local.user

import android.content.Context
import android.content.SharedPreferences

class UserPreference(context: Context) {

    private val preference: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(data: User) {
        val editor = preference.edit()
        editor.apply {
            putString(ID, data.id)
            apply()
        }
    }

    fun getUser(): User {
        val user = User()
        user.apply {
            id = preference.getString(ID, "")
        }

        return user
    }


    // Save auth token
//    fun saveAuthToken(token: String) {
//        preferences.edit().putString(AUTH_TOKEN_KEY, token).apply()
//    }
//
//    // Get auth token
//    fun getAuthToken(): String? = preferences.getString(AUTH_TOKEN_KEY, null)
//
//    // Clear auth token
//    fun clearAuthToken() {
//        preferences.edit().remove(AUTH_TOKEN_KEY).apply()
//    }

    companion object {
//        private const val AUTH_TOKEN_KEY = "authToken"
        private const val PREFS_NAME = "user_pref"
        private const val ID = "id"
    }
}
