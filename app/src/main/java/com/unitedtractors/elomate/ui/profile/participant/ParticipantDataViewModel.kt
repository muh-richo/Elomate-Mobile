package com.unitedtractors.elomate.ui.profile.participant

import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.repository.ElomateRepository

class ParticipantDataViewModel(private val repository: ElomateRepository) : ViewModel() {

    fun getParticipantData(token: String) =
        repository.getParticipantData(token)
}