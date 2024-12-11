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
data class HighestDataItem(

	@field:SerializedName("point_kirkpatrick")
	val pointKirkpatrick: String? = null,

	@field:SerializedName("data_label")
	val dataLabel: List<DataLabelItem?>? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("total_point")
	val totalPoint: String? = null
) : Parcelable

@Parcelize
data class LowestDataItem(

	@field:SerializedName("point_kirkpatrick")
	val pointKirkpatrick: String? = null,

	@field:SerializedName("data_label")
	val dataLabel: List<DataLabelItem?>? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("total_point")
	val totalPoint: String? = null
) : Parcelable

@Parcelize
data class KirkpatrickDetailItem(

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("data_detail")
	val dataDetail: DataDetail? = null
) : Parcelable

@Parcelize
data class DataDetail(

	@field:SerializedName("highest_data")
	val highestData: List<HighestDataItem?>? = null,

	@field:SerializedName("lowest_data")
	val lowestData: List<LowestDataItem?>? = null
) : Parcelable

@Parcelize
data class DataLabelItem(

	@field:SerializedName("average_score")
	val averageScore: String? = null,

	@field:SerializedName("label")
	val label: String? = null
) : Parcelable

