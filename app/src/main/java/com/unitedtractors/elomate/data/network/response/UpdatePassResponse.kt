package com.unitedtractors.elomate.data.network.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class UpdatePassResponse(

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable
