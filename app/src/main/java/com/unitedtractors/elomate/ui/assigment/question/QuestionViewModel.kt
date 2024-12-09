package com.unitedtractors.elomate.ui.assigment.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.request.AnswerMultipleChoiceRequest
import com.unitedtractors.elomate.data.network.response.MessageErrorResponse
import com.unitedtractors.elomate.data.network.response.QuestionResponse
import com.unitedtractors.elomate.data.network.response.SuccessResponse
import com.unitedtractors.elomate.data.repository.ElomateRepository

class QuestionViewModel(private val repository: ElomateRepository) : ViewModel() {

    fun getQuestion(token: String, assignmentId: Int): LiveData<Result<List<QuestionResponse>, MessageErrorResponse>> {
        return repository.getQuestionAssignment(token, assignmentId)
    }

    fun submitAnswerMultipleChoice(token: String, assignmentId: Int, answers: List<AnswerMultipleChoiceRequest>): LiveData<Result<SuccessResponse, MessageErrorResponse>> {
        return repository.submitAnswerMultiple(token, assignmentId, answers)
    }

    fun submitAnswerEssay(token: String, assignmentId: Int, essayAnswers: String, filePath: String): LiveData<Result<SuccessResponse, MessageErrorResponse>> {
        return repository.submitAnswerEssay(token, assignmentId, essayAnswers, filePath)
    }
}