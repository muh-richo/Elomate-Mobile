package com.unitedtractors.elomate.ui.profile.displayprofile

import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.repository.ElomateRepository

class ProfileViewModel(private val repository: ElomateRepository) : ViewModel() {

    fun getCurrentUserApi(token: String) =
        repository.getCurrentUserApi(token)

}