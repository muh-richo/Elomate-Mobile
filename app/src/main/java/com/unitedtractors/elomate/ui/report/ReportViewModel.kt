package com.unitedtractors.elomate.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.response.ListCourseItem
import com.unitedtractors.elomate.data.network.response.MessageErrorResponse
import com.unitedtractors.elomate.data.network.response.PhaseResponse
import com.unitedtractors.elomate.data.network.response.ReportResponse
import com.unitedtractors.elomate.data.network.response.TopicResponse
import com.unitedtractors.elomate.data.repository.ElomateRepository

class ReportViewModel(private val repository: ElomateRepository) : ViewModel() {

    fun getCurrentUserApi(token: String) = repository.getCurrentUserApi(token)

    fun getPhaseUser(token: String): LiveData<Result<List<PhaseResponse>, MessageErrorResponse>> {
        return repository.getPhaseUser(token)
    }

    fun getTopicUser(token: String, phaseId: Int): LiveData<Result<List<TopicResponse>, MessageErrorResponse>> {
        return repository.getTopicUser(token, phaseId)
    }

    fun getReportUser(token: String, phaseId: Int, topicId: Int): LiveData<Result<ReportResponse, MessageErrorResponse>> {
        return repository.getReportByPhaseIdTopicId(token, phaseId, topicId)
    }
}