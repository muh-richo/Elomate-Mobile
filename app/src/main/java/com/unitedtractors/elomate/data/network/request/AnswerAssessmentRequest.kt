package com.unitedtractors.elomate.data.network.request

import com.google.gson.annotations.SerializedName

data class AnswerAssessmentRequest(
    @SerializedName("question_id") val questionId: Int,
    @SerializedName("answer_likert") val answerLikert: String,
)
