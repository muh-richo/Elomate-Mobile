package com.unitedtractors.elomate.ui.assigment.preactivity.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.response.MessageErrorResponse
import com.unitedtractors.elomate.data.network.response.PreActivityResponse
import com.unitedtractors.elomate.data.repository.ElomateRepository

class PreActivityViewModel(private val repository: ElomateRepository) : ViewModel() {
    fun getPreActivityByCourseId(token: String, courseId: Int): LiveData<Result<List<PreActivityResponse>, MessageErrorResponse>> {
        return repository.getPreActivityByCourseId(token, courseId)
    }
}