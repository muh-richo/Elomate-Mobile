package com.unitedtractors.elomate.ui.assessment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.network.response.QuestionAssessmentResponse
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.request.AnswerAssessmentRequest
import com.unitedtractors.elomate.data.network.response.AssessmentResponse
import com.unitedtractors.elomate.data.network.response.AssessmentsItem
import com.unitedtractors.elomate.data.network.response.MessageErrorResponse
import com.unitedtractors.elomate.data.network.response.PeerAssessmentResponse
import com.unitedtractors.elomate.data.network.response.SuccessResponse
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

    fun getQuestionAssessment(token: String, assessmentId: Int): LiveData<Result<QuestionAssessmentResponse, MessageErrorResponse>> {
        return repository.getQuestionAssessment(token, assessmentId)
    }

    fun getListPeerAssessment(token: String, assessmentId: Int): LiveData<Result<PeerAssessmentResponse, MessageErrorResponse>> {
        return repository.getListPeerAssessment(token, assessmentId)
    }

    fun postAnswerSelfAssessment(token: String, assessmentId: Int, answer: List<AnswerAssessmentRequest>): LiveData<Result<SuccessResponse, MessageErrorResponse>> {
        return repository.postAnswerSelfAssessment(token, assessmentId, answer)
    }

    fun postAnswerPeerAssessment(token: String, assessmentId: Int, peerId: Int, answer: List<AnswerAssessmentRequest>): LiveData<Result<SuccessResponse, MessageErrorResponse>> {
        return repository.postAnswerPeerAssessment(token, assessmentId, peerId, answer)
    }


}