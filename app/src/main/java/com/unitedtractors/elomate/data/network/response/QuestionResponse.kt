package com.unitedtractors.elomate.data.network.response

import com.google.gson.annotations.SerializedName

data class QuestionResponse(

	@field:SerializedName("question_text")
	val questionText: String? = null,

	@field:SerializedName("question_type")
	val questionType: String? = null,

	@field:SerializedName("all_options")
	val allOptions: List<String?>? = null,

	@field:SerializedName("question_id")
	val questionId: Int? = null
)
