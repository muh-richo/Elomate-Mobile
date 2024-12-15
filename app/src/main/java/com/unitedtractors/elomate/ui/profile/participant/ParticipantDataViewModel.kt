package com.unitedtractors.elomate.ui.profile.participant

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.response.EducationResponse
import com.unitedtractors.elomate.data.network.response.MessageErrorResponse
import com.unitedtractors.elomate.data.repository.ElomateRepository

class ParticipantDataViewModel(private val repository: ElomateRepository) : ViewModel() {

    fun getParticipantData(token: String) =
        repository.getParticipantData(token)

    fun getParticipantEducation(token: String, userId: Int) : LiveData<Result<List<EducationResponse>, MessageErrorResponse>> {
        return repository.getParticipantEducation(token, userId)
    }
}