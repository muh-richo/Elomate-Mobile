package com.unitedtractors.elomate.ui.auth.login

import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.repository.ElomateRepository

class LoginViewModel(private val repository: ElomateRepository): ViewModel() {
    fun login(email: String, password: String) =
        repository.loginApi(email, password)

}