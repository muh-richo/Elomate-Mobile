package com.unitedtractors.elomate.ui.assignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.repository.ElomateRepository
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.response.CourseResponse
import com.unitedtractors.elomate.data.network.response.MessageErrorResponse
import com.unitedtractors.elomate.data.network.response.PhaseResponse
import com.unitedtractors.elomate.data.network.response.TopicResponse

class CourseViewModel(private val repository: ElomateRepository) : ViewModel() {
    fun getCurrentUserApi(token: String) = repository.getCurrentUserApi(token)

    fun getPhaseUser(token: String): LiveData<Result<List<PhaseResponse>, MessageErrorResponse>> {
        return repository.getPhaseUser(token)
    }

    fun getTopicUser(token: String, phaseId: Int): LiveData<Result<List<TopicResponse>, MessageErrorResponse>> {
        return repository.getTopicUser(token, phaseId)
    }

    fun getCourseByPhaseIdTopicId(token: String, phaseId: Int, topicId: Int): LiveData<Result<List<CourseResponse>, MessageErrorResponse>> {
        return repository.getCourseByPhaseIdTopicId(token, phaseId, topicId)
    }

    fun getCourseById(token: String, courseId: Int): LiveData<Result<List<CourseResponse>, MessageErrorResponse>> {
        return repository.getCourseById(token, courseId)
    }

    fun getAllCourses(token: String): LiveData<Result<List<CourseResponse>, MessageErrorResponse>> {
        return repository.getAllCourses(token)
    }

}