package com.unitedtractors.elomate.data.network.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class MentoringResponse(

	@field:SerializedName("course_id")
	val courseId: Int? = null,

	@field:SerializedName("jam_mulai")
	val jamMulai: String? = null,

	@field:SerializedName("jam_selesai")
	val jamSelesai: String? = null,

	@field:SerializedName("tipe_mentoring")
	val tipeMentoring: String? = null,

	@field:SerializedName("metode_mentoring")
	val metodeMentoring: String? = null,

	@field:SerializedName("lesson_learned_competencies")
	val lessonLearnedCompetencies: String? = null,

	@field:SerializedName("catatan_mentor")
	val catatanMentor: String? = null,

	@field:SerializedName("mentor_id")
	val mentorId: Int? = null,

	@field:SerializedName("kompetensi_yang_dievaluasi")
	val kompetensiYangDievaluasi: String? = null,

	@field:SerializedName("nama_topik")
	val namaTopik: String? = null,

	@field:SerializedName("nama_course")
	val namaCourse: String? = null,

	@field:SerializedName("topik_id")
	val topikId: Int? = null,

	@field:SerializedName("waktu_mentoring")
	val waktuMentoring: String? = null,

	@field:SerializedName("mentoring_id")
	val mentoringId: Int? = null,

	@field:SerializedName("tanggal_mentoring")
	val tanggalMentoring: String? = null,

	@field:SerializedName("nama_fasilitator")
	val namaFasilitator: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable
