package com.unitedtractors.elomate.ui.assessment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.response.AssessmentResponse
import com.unitedtractors.elomate.data.network.response.AssessmentsItem
import com.unitedtractors.elomate.data.network.response.MessageErrorResponse
import com.unitedtractors.elomate.data.repository.ElomateRepository

class AssessmentViewModel(private val repository: ElomateRepository) : ViewModel() {

    fun getAssessment(token: String): LiveData<Result<List<AssessmentResponse>, MessageErrorResponse>> {
        return repository.getAssessment(token)
    }

    fun getSelfAssessment(token: String): LiveData<Result<List<AssessmentsItem>, MessageErrorResponse>> {
        return repository.getSelfAssessment(token)
    }

    fun getPeerAssessment(token: String): LiveData<Result<List<AssessmentsItem>, MessageErrorResponse>> {
        return repository.getPeerAssessment(token)
    }
}