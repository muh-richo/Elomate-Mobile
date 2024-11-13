package com.unitedtractors.elomate.data.network.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CourseResponse(

	@field:SerializedName("CourseResponse")
	val courseResponse: List<CourseResponseItem?>? = null
) : Parcelable

@Parcelize
data class CourseResponseItem(

	@field:SerializedName("course_id")
	val courseId: Int? = null,

	@field:SerializedName("batch_name")
	val batchName: String? = null,

	@field:SerializedName("nama_course")
	val namaCourse: String? = null,

	@field:SerializedName("nama_topik")
	val namaTopik: String? = null,

	@field:SerializedName("fasilitator_name")
	val fasilitatorName: String? = null,

	@field:SerializedName("progress")
	val progress: Int? = null,

	@field:SerializedName("mentee_name")
	val menteeName: String? = null
) : Parcelable
