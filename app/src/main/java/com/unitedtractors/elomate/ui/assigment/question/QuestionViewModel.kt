package com.unitedtractors.elomate.ui.assigment.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.request.AnswerMultipleChoiceRequest
import com.unitedtractors.elomate.data.network.response.MessageErrorResponse
import com.unitedtractors.elomate.data.network.response.MultipleChoiceQuestionResponse
import com.unitedtractors.elomate.data.network.response.SuccessResponse
import com.unitedtractors.elomate.data.repository.ElomateRepository

class QuestionViewModel(private val repository: ElomateRepository) : ViewModel() {

    fun getPilganQuestion(token: String, assignmentId: Int): LiveData<Result<List<MultipleChoiceQuestionResponse>, MessageErrorResponse>> {
        return repository.getPilganQuestionAssignment(token, assignmentId)
    }

    fun submitAnwerMutliple(token: String, assignmentId: Int, answers: List<AnswerMultipleChoiceRequest>): LiveData<Result<SuccessResponse, MessageErrorResponse>> {
        return repository.submitAnswerMultiple(token, assignmentId, answers)
    }
}