package com.unitedtractors.elomate.data.network.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class KirkPatrickResponse(

	@field:SerializedName("selfAssessment")
	val selfAssessment: SelfAssessment? = null,

	@field:SerializedName("peerAssessment")
	val peerAssessment: PeerAssessment? = null
) : Parcelable

@Parcelize
data class DataItem(

	@field:SerializedName("data")
	val data: List<DataAssessment?>? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("point_kirkpatrick")
	val pointKirkpatrick: String? = null,

	@field:SerializedName("average_score")
	val averageScore: String? = null
) : Parcelable

@Parcelize
data class SelfAssessment(

	@field:SerializedName("data")
	val data: List<DataAssessment?>? = null,

	@field:SerializedName("label")
	val label: String? = null
) : Parcelable

@Parcelize
data class PeerAssessment(

	@field:SerializedName("data")
	val data: List<DataAssessment?>? = null,

	@field:SerializedName("label")
	val label: String? = null
) : Parcelable
