package com.unitedtractors.elomate.data.network.retrofit

import com.unitedtractors.elomate.data.network.request.AnswerMultipleChoiceRequest
import com.unitedtractors.elomate.data.network.request.AnswerAssessmentRequest
import com.unitedtractors.elomate.data.network.response.QuestionAssessmentResponse
import com.unitedtractors.elomate.data.network.request.EducationRequest
import com.unitedtractors.elomate.data.network.request.FormFeedbackMentoringRequest
import com.unitedtractors.elomate.data.network.response.AssessmentResponse
import com.unitedtractors.elomate.data.network.response.AssessmentsItem
import com.unitedtractors.elomate.data.network.response.AssignmentResponse
import com.unitedtractors.elomate.data.network.response.CourseResponse
import com.unitedtractors.elomate.data.network.request.LoginRequest
import com.unitedtractors.elomate.data.network.request.MentoringRequest
import com.unitedtractors.elomate.data.network.response.MentoringResponse
import com.unitedtractors.elomate.data.network.response.EducationResponse
import com.unitedtractors.elomate.data.network.response.PhaseResponse
import com.unitedtractors.elomate.data.network.response.PostActivityResponse
import com.unitedtractors.elomate.data.network.response.PreActivityResponse
import com.unitedtractors.elomate.data.network.response.PreReadingResponse
import com.unitedtractors.elomate.data.network.response.ReportResponse
import com.unitedtractors.elomate.data.network.response.TokenResponse
import com.unitedtractors.elomate.data.network.response.TopicResponse
import com.unitedtractors.elomate.data.network.request.UpdatePasswordRequest
import com.unitedtractors.elomate.data.network.response.SuccessResponse
import com.unitedtractors.elomate.data.network.request.UpdateProfileRequest
import com.unitedtractors.elomate.data.network.response.KirkPatrickResponse
import com.unitedtractors.elomate.data.network.response.KirkpatrickDetailResponse
import com.unitedtractors.elomate.data.network.response.ListActivityItem
import com.unitedtractors.elomate.data.network.response.NotificationResponse
import com.unitedtractors.elomate.data.network.response.PeerAssessmentResponse
import com.unitedtractors.elomate.data.network.response.QuestionResponse
import com.unitedtractors.elomate.data.network.response.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @GET("auth/me/education")
    suspend fun getEducation(
        @Header("Authorization") token: String
    ): List<EducationResponse>

    @GET("auth/me/education/{educationId}")
    suspend fun getEducationById(
        @Header("Authorization") token: String,
        @Path("educationId") educationId: Int
    ): EducationResponse

    @GET("auth/me/education/level")
    suspend fun getEducationLevel(
        @Header("Authorization") token: String
    ): List<String>

    @POST("auth/me/education")
    suspend fun addEducation(
        @Header("Authorization") token: String,
        @Body education: EducationRequest
    ): SuccessResponse

    @PATCH("auth/me/education/{educationId}")
    suspend fun updateEducation(
        @Header("Authorization") token: String,
        @Path("educationId") educationId: Int,
        @Body education: EducationRequest
    ): SuccessResponse

    @DELETE("auth/me/education/{educationId}")
    suspend fun deleteEducation(
        @Header("Authorization") token: String,
        @Path("educationId") educationId: Int
    ): SuccessResponse

    @GET("participantData")
    suspend fun getParticipantData(
        @Header("Authorization") token: String,
    ): List<UserResponse>

    @GET("participantData/education/{userId}")
    suspend fun getParticipantEducation(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int,
    ): List<EducationResponse>

    @GET("notification/all")
    suspend fun getNotification(
        @Header("Authorization") token: String,
    ): NotificationResponse

    @GET("assignment/user/todo")
    suspend fun getToDoList(
        @Header("Authorization") token: String
    ): List<AssignmentResponse>

    @GET("assignment/user/todoSchedule/{deadline}")
    suspend fun getToDoListSchedule(
        @Header("Authorization") token: String,
        @Path("deadline") deadline: String
    ): List<ListActivityItem>

    @GET("courses/courseProgress")
    suspend fun getCourseProgress(
        @Header("Authorization") token: String
    ): List<CourseResponse>

    @GET("mentoring/courses")
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

    @GET("assignmentTask/question/{assignmentId}")
    suspend fun getQuestionAssignment(
        @Header("Authorization") token: String,
        @Path("assignmentId") assignmentId: Int,
    ): List<QuestionResponse>

    @POST("assignmentTask/answerMultiple/{assignmentId}")
    suspend fun submitAnswerMultipleChoice(
        @Header("Authorization") token: String,
        @Path("assignmentId") assignmentId: Int,
        @Body answer: List<AnswerMultipleChoiceRequest>
    ): SuccessResponse

    @Multipart
    @POST("assignmentTask/answerEssay/{assignmentId}")
    suspend fun submitAnswerEssay(
        @Header("Authorization") token: String,
        @Path("assignmentId") assignmentId: Int,
        @Part("essay_answer") essayAnswer: RequestBody,
        @Part lampiranFile: MultipartBody.Part
    ): SuccessResponse

    @Multipart
    @POST("assignmentTask/answerEssay/{assignmentId}")
    suspend fun submitAnswerEssayWithoutFile(
        @Header("Authorization") token: String,
        @Path("assignmentId") assignmentId: Int,
        @Part("essay_answer") essayAnswer: RequestBody
    ): SuccessResponse

    @GET("report/{phaseId}/{topicId}")
    suspend fun getReportByPhaseIdTopicId(
        @Header("Authorization") token: String,
        @Path("phaseId") phaseId: Int,
        @Path("topicId") topicId: Int,
    ): ReportResponse

    @GET("report/kirkpatrick")
    suspend fun getKirkpatrickReport(
        @Header("Authorization") token: String,
    ): KirkPatrickResponse

    @GET("report/kirkpatrickDetailQuestion")
    suspend fun getKirkpatrickDetail(
        @Header("Authorization") token: String,
    ): KirkpatrickDetailResponse

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
    ): MutableList<MentoringResponse>

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

    @PATCH("mentoring/insertFeedback/{mentoringId}")
    suspend fun submitFormFeedbackMentoring(
        @Header("Authorization") token: String,
        @Path("mentoringId") mentoringId: Int,
        @Body feedbackMentoring: FormFeedbackMentoringRequest,
    ): SuccessResponse

    @DELETE("mentoring/delete/{mentoringId}")
    suspend fun deleteMentoring(
        @Header("Authorization") token: String,
        @Path("mentoringId") mentoringId: Int,
    ): SuccessResponse

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

    @GET("assessment/question/{assessmentId}")
    suspend fun getQuestionAssessment(
        @Header("Authorization") token: String,
        @Path("assessmentId") assessmentId: Int
    ): QuestionAssessmentResponse

    @GET("assessment/statusPeerParticipant/{assessmentId}")
    suspend fun getPeerAssessmentList(
        @Header("Authorization") token: String,
        @Path("assessmentId") assessmentId: Int
    ): PeerAssessmentResponse

    @POST("assessment/selfAssessment/{assessmentId}")
    suspend fun postSelfAssessmentAnswer(
        @Header("Authorization") token: String,
        @Path("assessmentId") assessmentId: Int,
        @Body question: List<AnswerAssessmentRequest>
    ): SuccessResponse

    @POST("assessment/peerAssessment/{assessmentId}/{peerId}")
    suspend fun postPeerAssessmentAnswer(
        @Header("Authorization") token: String,
        @Path("assessmentId") assessmentId: Int,
        @Path("peerId") peerId: Int,
        @Body question: List<AnswerAssessmentRequest>
    ): SuccessResponse



    @GET
    fun downloadPdfFile(
        @Url pdfUrl: String
    ): Call<ResponseBody>
}
