package com.unitedtractors.elomate.data.network.request

data class UpdatePasswordRequest(
    val currentPassword: String,
    val newPassword: String
)
