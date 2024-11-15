package com.unitedtractors.elomate.data.network.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class TopicResponse(

	@field:SerializedName("nama_topik")
	val namaTopik: String? = null,

	@field:SerializedName("topik_id")
	val topikId: Int? = null
) : Parcelable
