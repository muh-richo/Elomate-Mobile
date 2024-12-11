package com.unitedtractors.elomate.data.network.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class NotificationResponse(

	@field:SerializedName("data_notif")
	val dataNotif: List<DataNotifItem?>? = null
) : Parcelable

@Parcelize
data class DataNotifItem(

	@field:SerializedName("notification_type")
	val notificationType: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("deadline")
	val deadline: String? = null
) : Parcelable
