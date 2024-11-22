package com.unitedtractors.elomate.data.network.response

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("asal_universitas") val asalUniversitas: String,
    @SerializedName("jurusan") val jurusan: String,
    @SerializedName("jenjang_studi") val jenjangStudi: String,
    @SerializedName("tahun_lulus") val tahunLulus: String,
    @SerializedName("domisili") val domisili: String,
    @SerializedName("tempat_lahir") val tempatLahir: String,
    @SerializedName("tanggal_lahir") val tanggalLahir: String,
    @SerializedName("no_hp") val noHp: String
)
