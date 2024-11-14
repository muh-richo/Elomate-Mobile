package com.unitedtractors.elomate.ui.assignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.network.response.CourseResponseItem
import com.unitedtractors.elomate.data.repository.ElomateRepository
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.response.MessageErrorResponse
import com.unitedtractors.elomate.data.network.response.PhaseResponse

class CourseViewModel(private val repository: ElomateRepository) : ViewModel() {
    fun getCurrentUserApi(token: String) = repository.getCurrentUserApi(token)

    fun getPhaseUser(token: String): LiveData<Result<List<PhaseResponse>, MessageErrorResponse>> {
        return repository.getPhaseUser(token)
    }

    fun getCourses(token: String): LiveData<Result<List<CourseResponseItem>, MessageErrorResponse>> {
        return repository.getCourses(token)
    }

}