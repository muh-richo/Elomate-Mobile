package com.unitedtractors.elomate.ui.profile.changepassword

import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.repository.ElomateRepository

class ChangePasswordViewModel(private val repository: ElomateRepository): ViewModel() {

    fun updateChangePassword(token: String, currentPassword: String, newPassword: String) =
        repository.updatePassword(token, currentPassword, newPassword)
}