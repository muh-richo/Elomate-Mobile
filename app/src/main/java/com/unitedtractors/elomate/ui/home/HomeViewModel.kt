package com.unitedtractors.elomate.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.response.AssignmentResponse
import com.unitedtractors.elomate.data.network.response.MessageErrorResponse
import com.unitedtractors.elomate.data.network.response.PreActivityResponse
import com.unitedtractors.elomate.data.repository.ElomateRepository

class HomeViewModel(private val repository: ElomateRepository) : ViewModel() {

    fun getCurrentUserApi(token: String) = repository.getCurrentUserApi(token)

    fun getToDoList(token: String): LiveData<Result<List<AssignmentResponse>, MessageErrorResponse>> {
        return repository.getToDoList(token)
    }

}