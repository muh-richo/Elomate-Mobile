package com.unitedtractors.elomate.ui.assigment.postactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.response.AssignmentResponse
import com.unitedtractors.elomate.data.network.response.MessageErrorResponse
import com.unitedtractors.elomate.data.repository.ElomateRepository

class PostActivityViewModel(private val repository: ElomateRepository) : ViewModel() {
    fun getPostActivityByCourseId(token: String, courseId: Int): LiveData<Result<List<AssignmentResponse>, MessageErrorResponse>> {
        return repository.getPostActivityByCourseId(token, courseId)
    }
}