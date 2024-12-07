package com.unitedtractors.elomate.data.network.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PeerAssessmentResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("assessment_title")
	val assessmentTitle: String? = null,

	@field:SerializedName("overall_status")
	val overallStatus: String? = null,

	@field:SerializedName("assessment_id")
	val assessmentId: String? = null
) : Parcelable

@Parcelize
data class DataItem(

	@field:SerializedName("role_name")
	val roleName: String? = null,

	@field:SerializedName("status_peer_assessment")
	val statusPeerAssessment: String? = null,

	@field:SerializedName("batch_name")
	val batchName: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("posisi")
	val posisi: String? = null,

	@field:SerializedName("nrp")
	val nrp: String? = null,

	@field:SerializedName("email")
	val email: String? = null
) : Parcelable
