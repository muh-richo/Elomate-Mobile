package com.unitedtractors.elomate.data.network.retrofit

import com.unitedtractors.elomate.data.network.response.AssignmentResponse
import com.unitedtractors.elomate.data.network.response.CourseResponse
import com.unitedtractors.elomate.data.network.response.LoginRequest
import com.unitedtractors.elomate.data.network.response.PhaseResponse
import com.unitedtractors.elomate.data.network.response.PostActivityResponse
import com.unitedtractors.elomate.data.network.response.PreActivityResponse
import com.unitedtractors.elomate.data.network.response.PreReadingResponse
import com.unitedtractors.elomate.data.network.response.ReportResponse
import com.unitedtractors.elomate.data.network.response.TokenResponse
import com.unitedtractors.elomate.data.network.response.TopicResponse
import com.unitedtractors.elomate.data.network.response.UserResponse
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



    @GET("participantData")
    suspend fun getParticipantData(
        @Header("Authorization") token: String,
    ): List<UserResponse>



}
