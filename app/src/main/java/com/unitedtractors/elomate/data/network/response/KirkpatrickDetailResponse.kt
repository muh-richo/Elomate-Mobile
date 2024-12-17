package com.unitedtractors.elomate.data.network.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class KirkpatrickDetailResponse(

	@field:SerializedName("kirkpatrick_detail")
	val kirkpatrickDetail: List<KirkpatrickDetailItem?>? = null
) : Parcelable

@Parcelize
data class LowestDataItem(

	@field:SerializedName("point_kirkpatrick")
	val pointKirkpatrick: String? = null,

	@field:SerializedName("score")
	val score: String? = null,

	@field:SerializedName("question")
	val question: String? = null
) : Parcelable

@Parcelize
data class HighestDataItem(

	@field:SerializedName("point_kirkpatrick")
	val pointKirkpatrick: String? = null,

	@field:SerializedName("score")
	val score: String? = null,

	@field:SerializedName("question")
	val question: String? = null
) : Parcelable

@Parcelize
data class KirkpatrickDetailItem(

	@field:SerializedName("highest_data")
	val highestData: List<HighestDataItem?>? = null,

	@field:SerializedName("lowest_data")
	val lowestData: List<LowestDataItem?>? = null,

	@field:SerializedName("category")
	val category: String? = null
) : Parcelable