package com.unitedtractors.elomate.data.network.response

import com.google.gson.annotations.SerializedName

data class MentoringRequest (
    @SerializedName("nama_course") val namaCourse: String,
    @SerializedName("tanggal_mentoring") val tanggalMentoring: String,
    @SerializedName("jam_mulai") val jamMulai: String,
    @SerializedName("jam_selesai") val jamSelesai: String,
    @SerializedName("metode_mentoring") val metodeMentoring: String,
    @SerializedName("tipe_mentoring") val tipeMentoring: String,
)