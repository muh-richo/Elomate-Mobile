package com.unitedtractors.elomate.ui.profile.riwayatpendidikan

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.response.EducationResponse
import com.unitedtractors.elomate.data.network.response.MessageErrorResponse
import com.unitedtractors.elomate.data.network.response.SuccessResponse
import com.unitedtractors.elomate.data.repository.ElomateRepository

class EducationViewModel(private val repository: ElomateRepository) : ViewModel() {

    fun getEducation(token: String) : LiveData<Result<List<EducationResponse>, MessageErrorResponse>> {
        return repository.getEducation(token)
    }

    fun addEducation(token: String, universitas: String, jurusan: String, jenjangStudi: String, tahun_lulus: String) : LiveData<Result<SuccessResponse, MessageErrorResponse>> {
        return repository.addEducation(token, universitas, jurusan, jenjangStudi, tahun_lulus)
    }

    fun updateEducation(token: String, educationId: Int, universitas: String, jurusan: String, jenjangStudi: String, tahun_lulus: String) : LiveData<Result<SuccessResponse, MessageErrorResponse>> {
        return repository.updateEducation(token, educationId, universitas, jurusan, jenjangStudi, tahun_lulus)
    }

    fun deleteEducation(token: String, educationId: Int) : LiveData<Result<SuccessResponse, MessageErrorResponse>> {
        return repository.deleteEducation(token, educationId)
    }

}