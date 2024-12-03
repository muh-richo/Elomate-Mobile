package com.unitedtractors.elomate.data.network.retrofit

import com.unitedtractors.elomate.data.network.response.AssessmentResponse
import com.unitedtractors.elomate.data.network.response.AssessmentsItem
import com.unitedtractors.elomate.data.network.response.AssignmentResponse
import com.unitedtractors.elomate.data.network.response.CourseResponse
import com.unitedtractors.elomate.data.network.response.LoginRequest
import com.unitedtractors.elomate.data.network.response.MentoringRequest
import com.unitedtractors.elomate.data.network.response.MentoringResponse
import com.unitedtractors.elomate.data.network.response.PhaseResponse
import com.unitedtractors.elomate.data.network.response.PostActivityResponse
import com.unitedtractors.elomate.data.network.response.PreActivityResponse
import com.unitedtractors.elomate.data.network.response.PreReadingResponse
import com.unitedtractors.elomate.data.network.response.ReportResponse
import com.unitedtractors.elomate.data.network.response.TokenResponse
import com.unitedtractors.elomate.data.network.response.TopicResponse
import com.unitedtractors.elomate.data.network.response.UpdatePasswordRequest
import com.unitedtractors.elomate.data.network.response.SuccessResponse
import com.unitedtractors.elomate.data.network.response.UpdateProfileRequest
import com.unitedtractors.elomate.data.network.response.UserResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ElomateApiService {

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): TokenResponse

    @GET("auth/me")
    suspend fun getCurrentUser(
        @Header("Authorization") token: String
    ): UserResponse

    @PATCH("auth/me")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body updateProfile: UpdateProfileRequest
    ): SuccessResponse

    @PATCH("auth/me/password")
    suspend fun updatePassword(
        @Header("Authorization") token: String,
        @Body updatePasswordRequest: UpdatePasswordRequest
    ): SuccessResponse

    @GET("assignment/user/todo")
    suspend fun getToDoList(
        @Header("Authorization") token: String
    ): List<AssignmentResponse>

    @GET("courses")
    suspend fun getAllCoursesUser(
        @Header("Authorization") token: String
    ): List<CourseResponse>

    @GET("courses/phaseUser")
    suspend fun getPhaseUser(
        @Header("Authorization") token: String
    ): List<PhaseResponse>

    @GET("courses/phaseUser/{phaseId}")
    suspend fun getTopicByPhaseId(
        @Header("Authorization") token: String,
        @Path("phaseId") phaseId: Int,
    ): List<TopicResponse>

    @GET("courses/{phaseId}/{topicId}")
    suspend fun getCourseByPhaseIdTopicId(
        @Header("Authorization") token: String,
        @Path("phaseId") phaseId: Int,
        @Path("topicId") topicId: Int,
    ): List<CourseResponse>

    @GET("courses/{courseId}")
    suspend fun getCourseById(
        @Header("Authorization") token: String,
        @Path("courseId") courseId: Int,
    ): List<CourseResponse>

    @GET("assignment/preActivity/{courseId}")
    suspend fun getPreActivityByCourseId(
        @Header("Authorization") token: String,
        @Path("courseId") courseId: Int,
    ): List<PreActivityResponse>

    @GET("assignment/postActivity/{courseId}")
    suspend fun getPostActivityByCourseId(
        @Header("Authorization") token: String,
        @Path("courseId") courseId: Int,
    ): PostActivityResponse

    @GET("preReading/{courseId}")
    suspend fun getPreReadingByCourseId(
        @Header("Authorization") token: String,
        @Path("courseId") courseId: Int,
    ): List<PreReadingResponse>

    @GET("preReading/id/{preReadingId}")
    suspend fun getDetailPreReading(
        @Header("Authorization") token: String,
        @Path("preReadingId") preReadingId: Int,
    ): PreReadingResponse


    @GET
    fun downloadPdfFile(
        @Url pdfUrl: String
    ): Call<ResponseBody>



    @GET("assignment/id/{assignmentId}")
    suspend fun getDetailAssignment(
        @Header("Authorization") token: String,
        @Path("assignmentId") assignmentId: Int,
    ): AssignmentResponse

    @GET("report/{phaseId}/{topicId}")
    suspend fun getReportByPhaseIdTopicId(
        @Header("Authorization") token: String,
        @Path("phaseId") phaseId: Int,
        @Path("topicId") topicId: Int,
    ): ReportResponse

    @POST("mentoring/insert")
    suspend fun createMentoring(
        @Header("Authorization") token: String,
        @Body mentoring: MentoringRequest,
    ): SuccessResponse

    @GET("mentoring/method")
    suspend fun getMethodMentoring(
        @Header("Authorization") token: String,
    ): List<String>

    @GET("mentoring/type")
    suspend fun getTypeMentoring(
        @Header("Authorization") token: String,
    ): List<String>

    @GET("mentoring/upcomingData")
    suspend fun getUpcomingMentoring(
        @Header("Authorization") token: String,
    ): List<MentoringResponse>

    @GET("mentoring/feedbackData")
    suspend fun getFeedbackMentoring(
        @Header("Authorization") token: String,
    ): List<MentoringResponse>

    @GET("mentoring/approveData")
    suspend fun getApproveMentoring(
        @Header("Authorization") token: String,
    ): List<MentoringResponse>

    @GET("mentoring/mentoringId/{mentoringId}")
    suspend fun getDetailMentoring(
        @Header("Authorization") token: String,
        @Path("mentoringId") mentoringId: Int,
    ): MentoringResponse

    @GET("participantData")
    suspend fun getParticipantData(
        @Header("Authorization") token: String,
    ): List<UserResponse>

    @GET("assessment")
    suspend fun getAssessment(
        @Header("Authorization") token: String
    ): List<AssessmentResponse>

    @GET("assessment/selfAssessment")
    suspend fun getSelfAssessment(
        @Header("Authorization") token: String
    ): List<AssessmentsItem>

    @GET("assessment/peerAssessment")
    suspend fun getPeerAssessment(
        @Header("Authorization") token: String
    ): List<AssessmentsItem>

}
