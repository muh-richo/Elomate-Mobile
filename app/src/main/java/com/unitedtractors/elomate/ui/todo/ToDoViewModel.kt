package com.unitedtractors.elomate.ui.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.response.AssignmentResponse
import com.unitedtractors.elomate.data.network.response.MessageErrorResponse
import com.unitedtractors.elomate.data.repository.ElomateRepository

class ToDoViewModel(private val repository: ElomateRepository) : ViewModel() {

        fun getToDoList(token: String) : LiveData<Result<List<AssignmentResponse>, MessageErrorResponse>> {
            return repository.getToDoList(token)
    }

}