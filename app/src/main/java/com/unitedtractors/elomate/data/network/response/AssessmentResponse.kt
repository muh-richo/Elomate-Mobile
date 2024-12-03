package com.unitedtractors.elomate.data.network.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AssessmentResponse(

	@field:SerializedName("assessments")
	val assessments: List<AssessmentsItem?>? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("status_total")
	val statusTotal: String? = null,

	@field:SerializedName("nama_user")
	val namaUser: String? = null,

	@field:SerializedName("category")
	val category: String? = null
) : Parcelable

@Parcelize
data class AssessmentsItem(

	@field:SerializedName("tanggal_selesai")
	val tanggalSelesai: String? = null,

	@field:SerializedName("tanggal_mulai")
	val tanggalMulai: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("assessment_id")
	val assessmentId: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable
