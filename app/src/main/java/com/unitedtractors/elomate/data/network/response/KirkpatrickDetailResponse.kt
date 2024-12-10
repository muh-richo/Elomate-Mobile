package com.unitedtractors.elomate.data.network.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class KirkpatrickDetail(
	val category: String,
	val highestData: List<Triple<String, String, String>>,
	val lowestData: List<Triple<String, String, String>>
)

@Parcelize
data class KirkpatrickDetailResponse(

	@field:SerializedName("selfAssessmentDetail")
	val selfAssessmentDetail: SelfAssessmentDetail? = null,

	@field:SerializedName("peerAssessmentDetail")
	val peerAssessmentDetail: PeerAssessmentDetail? = null
) : Parcelable


@Parcelize
data class PeerAssessmentDetail(

	@field:SerializedName("detailKirkpatrick")
	val detailKirkpatrick: List<DetailKirkpatrickItem?>? = null,

	@field:SerializedName("label")
	val label: String? = null
) : Parcelable

@Parcelize
data class DetailKirkpatrickItem(

	@field:SerializedName("lowestData")
	val lowestData: List<LowestDataItem?>? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("highestData")
	val highestData: List<HighestDataItem?>? = null
) : Parcelable

@Parcelize
data class LowestDataItem(

	@field:SerializedName("point_kirkpatrick")
	val pointKirkpatrick: String? = null,

	@field:SerializedName("average_score")
	val averageScore: String? = null,

	@field:SerializedName("description")
	val description: String? = null
) : Parcelable

@Parcelize
data class SelfAssessmentDetail(

	@field:SerializedName("detailKirkpatrick")
	val detailKirkpatrick: List<DetailKirkpatrickItem?>? = null,

	@field:SerializedName("label")
	val label: String? = null
) : Parcelable

@Parcelize
data class HighestDataItem(

	@field:SerializedName("point_kirkpatrick")
	val pointKirkpatrick: String? = null,

	@field:SerializedName("average_score")
	val averageScore: String? = null,

	@field:SerializedName("description")
	val description: String? = null
) : Parcelable
