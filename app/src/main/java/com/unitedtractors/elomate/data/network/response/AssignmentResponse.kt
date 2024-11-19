package com.unitedtractors.elomate.data.network.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AssignmentResponse(

	@field:SerializedName("assignment_id")
	val assignmentId: Int? = null,

	@field:SerializedName("course_id")
	val courseId: Int? = null,

	@field:SerializedName("nama_course")
	val namaCourse: String? = null,

	@field:SerializedName("tanggal_selesai")
	val tanggalSelesai: String? = null,

	@field:SerializedName("total_questions")
	val totalQuestions: Int? = null,

	@field:SerializedName("question_type")
	val questionType: String? = null,

	@field:SerializedName("user_score")
	val userScore: Int? = null,

	@field:SerializedName("tanggal_mulai")
	val tanggalMulai: String? = null,

	@field:SerializedName("active")
	val active: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("category")
	val category: String? = null
) : Parcelable