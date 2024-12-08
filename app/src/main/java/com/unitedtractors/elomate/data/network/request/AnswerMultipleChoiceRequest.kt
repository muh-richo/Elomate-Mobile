package com.unitedtractors.elomate.data.network.request

import com.google.gson.annotations.SerializedName

data class AnswerMultipleChoiceRequest(
    @SerializedName("question_id") val questionId: Int,
    @SerializedName("user_answer") val userAnswer: String
)