package com.unitedtractors.elomate.data.network.request

import com.google.gson.annotations.SerializedName

data class FormFeedbackMentoringRequest(
    @SerializedName("lesson_learned_competencies") val lessonLearnedCompetencies: String,
    @SerializedName("catatan_mentor") val catatanMentor: String,
)
