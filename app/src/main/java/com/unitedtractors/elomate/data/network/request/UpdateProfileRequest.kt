package com.unitedtractors.elomate.data.network.request

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("email") val email: String,
    @SerializedName("divisi") val divisi: String,
    @SerializedName("posisi") val posisi: String,
    @SerializedName("domisili") val domisili: String,
    @SerializedName("tempat_lahir") val tempatLahir: String,
    @SerializedName("tanggal_lahir") val tanggalLahir: String,
    @SerializedName("no_hp") val noHp: String
)
