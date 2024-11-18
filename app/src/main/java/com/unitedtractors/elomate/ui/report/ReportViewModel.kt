package com.unitedtractors.elomate.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.response.MessageErrorResponse
import com.unitedtractors.elomate.data.network.response.PhaseResponse
import com.unitedtractors.elomate.data.repository.ElomateRepository

class ReportViewModel(private val repository: ElomateRepository) : ViewModel() {

    fun getCurrentUserApi(token: String) = repository.getCurrentUserApi(token)

    fun getPhaseUser(token: String): LiveData<Result<List<PhaseResponse>, MessageErrorResponse>> {
        return repository.getPhaseUser(token)
    }
}