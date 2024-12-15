package com.unitedtractors.elomate.data.local.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var token: String? = "",
    var rememberMe: Boolean = false
): Parcelable