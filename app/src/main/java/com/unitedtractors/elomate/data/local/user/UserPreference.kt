package com.unitedtractors.elomate.data.local.user

import android.content.Context
import android.content.SharedPreferences

class UserPreference(context: Context) {

    private val preference: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

//    fun setUser(user: User) {
//        val editor = preference.edit()
//        editor.apply {
//            putString(AUTH_TOKEN_KEY, user.token)
//            apply()
//        }
//    }

    fun getUser(): User {
        val user = User()
        user.apply {
            token = preference.getString(AUTH_TOKEN_KEY, "")
        }

        return user
    }

    fun saveAuthToken(token: String) {
        preference.edit().putString(AUTH_TOKEN_KEY, token).apply()
    }

    fun getAuthToken(): String? = preference.getString(AUTH_TOKEN_KEY, null)

    fun clearAuthToken() {
        preference.edit().remove(AUTH_TOKEN_KEY).apply()
    }

    companion object {
        private const val AUTH_TOKEN_KEY = "authToken"
        private const val PREFS_NAME = "USER_PREF"
    }
}
