package com.unitedtractors.elomate.ui.mentoring

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.response.CourseResponse
import com.unitedtractors.elomate.data.network.response.MentoringResponse
import com.unitedtractors.elomate.data.network.response.MessageErrorResponse
import com.unitedtractors.elomate.data.network.response.SuccessResponse
import com.unitedtractors.elomate.data.repository.ElomateRepository

class MentoringViewModel(private val repository: ElomateRepository) : ViewModel() {
    fun createMentoring(token: String, namaCourse: String, tanggalMentoring: String, jamMulai: String, jamSelesai: String, metodeMentoring: String, tipeMentoring: String): LiveData<Result<SuccessResponse, MessageErrorResponse>> {
        return repository.createMentoring(token, namaCourse, tanggalMentoring, jamMulai, jamSelesai, metodeMentoring, tipeMentoring)
    }

    fun getAllCourses(token: String): LiveData<Result<List<CourseResponse>, MessageErrorResponse>> {
        return repository.getAllCourses(token)
    }

    fun getMethodMentoring(token: String): LiveData<Result<List<String>, MessageErrorResponse>> {
        return repository.getMethodMentoring(token)
    }

    fun getTypeMentoring(token: String): LiveData<Result<List<String>, MessageErrorResponse>> {
        return repository.getTypeMentoring(token)
    }

    fun getUpcomingMentoring(token: String): LiveData<Result<MutableList<MentoringResponse>, MessageErrorResponse>> {
        return repository.getUpcomingMentoring(token)
    }

    fun getFeedbackMentoring(token: String): LiveData<Result<List<MentoringResponse>, MessageErrorResponse>> {
        return repository.getFeedbackMentoring(token)
    }

    fun getApproveMentoring(token: String): LiveData<Result<List<MentoringResponse>, MessageErrorResponse>> {
        return repository.getApproveMentoring(token)
    }

    fun getDetailMentoring(token: String, mentoringId: Int): LiveData<Result<MentoringResponse, MessageErrorResponse>> {
        return repository.getDetailMentoring(token, mentoringId)
    }

    fun submitFormFeedback(token: String, mentoringId: Int, lessonLearned: String, catatanMentor: String): LiveData<Result<SuccessResponse, MessageErrorResponse>> {
        return repository.submitFormMentoring(token, mentoringId, lessonLearned, catatanMentor)
    }

    fun deleteMentoring(token: String, mentoringId: Int): LiveData<Result<SuccessResponse, MessageErrorResponse>> {
        return repository.deleteMentoring(token, mentoringId)
    }
}