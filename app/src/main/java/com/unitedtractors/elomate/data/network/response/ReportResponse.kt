package com.unitedtractors.elomate.data.network.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ReportResponse(

	@field:SerializedName("rata-rata_semua_course")
	val rataRataSemuaCourse: Int? = null,

	@field:SerializedName("list_course")
	val listCourse: List<ListCourseItem?>? = null
) : Parcelable

@Parcelize
data class ListCourseItem(

	@field:SerializedName("course_id")
	val courseId: Int? = null,

	@field:SerializedName("nilai_total_course")
	val nilaiTotalCourse: Int? = null,

	@field:SerializedName("nama_course")
	val namaCourse: String? = null,

	@field:SerializedName("jumlah_assignment_incomplete")
	val jumlahAssignmentIncomplete: Int? = null,

	@field:SerializedName("jumlah_assignment_complete")
	val jumlahAssignmentComplete: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable
