package com.unitedtractors.elomate.ui.announcement

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.response.MessageErrorResponse
import com.unitedtractors.elomate.data.network.response.NotificationResponse
import com.unitedtractors.elomate.data.repository.ElomateRepository

class AnnouncementViewModel(private val repository: ElomateRepository) : ViewModel() {

    fun getAnnouncement(token: String) : LiveData<Result<NotificationResponse, MessageErrorResponse>> {
        return repository.getAnnouncement(token)
    }
}