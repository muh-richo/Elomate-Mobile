package com.unitedtractors.elomate.ui.assigment.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.response.AssignmentResponse
import com.unitedtractors.elomate.data.network.response.MessageErrorResponse
import com.unitedtractors.elomate.data.repository.ElomateRepository

class DetailAssignmentViewModel(private val repository: ElomateRepository) : ViewModel() {

    fun getDetailAssignment(token: String, assignmentId: Int): LiveData<Result<AssignmentResponse, MessageErrorResponse>> {
        return repository.getDetailAssignment(token, assignmentId)
    }
}