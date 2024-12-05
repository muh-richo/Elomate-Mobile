package com.unitedtractors.elomate.data.network.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class QuestionAssessmentResponse(

    @field:SerializedName("question")
	val question: List<QuestionItem?>? = null,

    @field:SerializedName("assessment_title")
	val assessmentTitle: String? = null,

    @field:SerializedName("assessment_id")
	val assessmentId: Int? = null
) : Parcelable

@Parcelize
data class QuestionItem(

	@field:SerializedName("question_text")
	val questionText: String? = null,

	@field:SerializedName("question_id")
	val questionId: Int? = null
) : Parcelable
