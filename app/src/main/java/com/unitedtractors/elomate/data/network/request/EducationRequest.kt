package com.unitedtractors.elomate.data.network.request

import com.google.gson.annotations.SerializedName

data class EducationRequest (
    @SerializedName("universitas") val universitas: String,
    @SerializedName("jurusan") val jurusan: String,
    @SerializedName("jenjang_studi") val jenjangStudi: String,
    @SerializedName("tahun_lulus") val tahunLulus: String
)