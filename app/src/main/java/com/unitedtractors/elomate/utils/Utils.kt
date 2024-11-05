package com.unitedtractors.elomate.utils

import android.util.Patterns

object Utils {
    fun isValidEmail(email: CharSequence): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}