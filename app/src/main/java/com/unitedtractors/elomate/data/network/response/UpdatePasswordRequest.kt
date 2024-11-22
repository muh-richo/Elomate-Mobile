package com.unitedtractors.elomate.data.network.response

data class UpdatePasswordRequest(
    val currentPassword: String,
    val newPassword: String
)
