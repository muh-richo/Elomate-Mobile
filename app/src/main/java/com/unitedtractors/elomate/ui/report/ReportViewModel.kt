package com.unitedtractors.elomate.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.repository.ElomateRepository

class ReportViewModel(private val repository: ElomateRepository) : ViewModel() {

    fun getCurrentUserApi(token: String) = repository.getCurrentUserApi(token)
}