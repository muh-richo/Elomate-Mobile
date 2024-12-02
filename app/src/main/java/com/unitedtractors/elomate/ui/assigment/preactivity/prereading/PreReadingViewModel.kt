package com.unitedtractors.elomate.ui.assigment.preactivity.prereading

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.response.MessageErrorResponse
import com.unitedtractors.elomate.data.network.response.PreReadingResponse
import com.unitedtractors.elomate.data.repository.ElomateRepository

class PreReadingViewModel(private val repository: ElomateRepository) : ViewModel() {
    fun getPreReadingByCourseId(token: String, courseId: Int): LiveData<Result<List<PreReadingResponse>, MessageErrorResponse>> {
        return repository.getPreReadingByCourseId(token, courseId)
    }

    fun getDetailPreReading(token: String, preReadingId: Int): LiveData<Result<PreReadingResponse, MessageErrorResponse>> {
        return repository.getDetailPreReading(token, preReadingId)
    }


}