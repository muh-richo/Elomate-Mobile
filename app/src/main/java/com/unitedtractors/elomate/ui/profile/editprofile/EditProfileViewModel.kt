package com.unitedtractors.elomate.ui.profile.editprofile

import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.repository.ElomateRepository

class EditProfileViewModel(private val repository: ElomateRepository) : ViewModel() {

    fun getCurrentUserApi(token: String) =
        repository.getCurrentUserApi(token)

    fun updateProfile(token: String, asalUniversitas: String, jurusan: String, jenjangStudi: String, tahunLulus: String, domisili: String, tempatLahir: String, tanggalLahir: String, noHp: String) =
        repository.updateProfile(token, asalUniversitas, jurusan, jenjangStudi, tahunLulus, domisili, tempatLahir, tanggalLahir, noHp)

}