package com.unitedtractors.elomate.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.unitedtractors.elomate.data.network.response.QuestionAssessmentResponse
import com.unitedtractors.elomate.data.network.response.MessageErrorResponse
import com.unitedtractors.elomate.data.network.response.TokenResponse
import com.unitedtractors.elomate.data.network.retrofit.ElomateApiService
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.request.AnswerSelfAssessmentRequest
import com.unitedtractors.elomate.data.network.request.EducationRequest
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
import com.unitedtractors.elomate.data.network.response.TopicResponse
import com.unitedtractors.elomate.data.network.request.UpdatePasswordRequest
import com.unitedtractors.elomate.data.network.response.SuccessResponse
import com.unitedtractors.elomate.data.network.request.UpdateProfileRequest
import com.unitedtractors.elomate.data.network.response.PeerAssessmentResponse
import com.unitedtractors.elomate.data.network.response.UserResponse
import retrofit2.HttpException
import java.net.SocketTimeoutException

class ElomateRepository(
    private val elomateApiService: ElomateApiService
) {
    fun loginApi(email: String, password: String): LiveData<Result<TokenResponse, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val loginRequest = LoginRequest(email, password)
                val response = elomateApiService.login(loginRequest)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e(TAG, "Error Login API : $errorBody")
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                val errorMessage = messageErrorResponse.message
                emit(Result.Error(MessageErrorResponse(errorMessage)))
            } catch (e: SocketTimeoutException) {
                Log.e(TAG, "Timeout Login with API : ${e.message}", )
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
    }

    fun updateProfile(token: String, domisili: String, tempatLahir: String, tanggalLahir: String, noHp: String): LiveData<Result<SuccessResponse, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val updateProfileRequest = UpdateProfileRequest(domisili, tempatLahir, tanggalLahir, noHp)
                val response = elomateApiService.updateProfile(token, updateProfileRequest)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e(TAG, "Error Update Profile : $errorBody")
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                val errorMessage = messageErrorResponse.message
                emit(Result.Error(MessageErrorResponse(errorMessage)))
            }
        }

    fun updatePassword(token: String, currentPassword: String, newPassword: String): LiveData<Result<SuccessResponse, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val updatePasswordRequest = UpdatePasswordRequest(currentPassword, newPassword)
                val response = elomateApiService.updatePassword(token, updatePasswordRequest)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e(TAG, "Error Update Password API : $errorBody")
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                val errorMessage = messageErrorResponse.message
                emit(Result.Error(MessageErrorResponse(errorMessage)))
            }
        }

    fun getCurrentUserApi(token: String): LiveData<Result<UserResponse, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = elomateApiService.getCurrentUser(token)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e(TAG, "Error Get Current API : $errorBody")
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                val errorMessage = messageErrorResponse.message
                emit(Result.Error(MessageErrorResponse(errorMessage)))
            } catch (e: SocketTimeoutException) {
                Log.e(TAG, "Timeout Get Current User with API : ${e.message}", )
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
    }

    fun getParticipantData(token: String): LiveData<Result<List<UserResponse>, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = elomateApiService.getParticipantData(token)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
    }

    fun getEducation(token: String): LiveData<Result<List<EducationResponse>, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = elomateApiService.getEducation(token)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
        }

    fun addEducation(token: String, universitas: String, jurusan: String, jenjangStudi: String, tahunLulus: String): LiveData<Result<SuccessResponse, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val educationRequest = EducationRequest(universitas, jurusan, jenjangStudi, tahunLulus)
                val response = elomateApiService.addEducation(token, educationRequest)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
        }

    fun updateEducation(token: String, educationId: Int, universitas: String, jurusan: String, jenjangStudi: String, tahunLulus: String): LiveData<Result<SuccessResponse, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val educationRequest = EducationRequest(universitas, jurusan, jenjangStudi, tahunLulus)
                val response = elomateApiService.updateEducation(token, educationId, educationRequest)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
        }

    fun deleteEducation(token: String, educationId: Int): LiveData<Result<SuccessResponse, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = elomateApiService.deleteEducation(token, educationId)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse =
                    Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
        }

    fun getToDoList(token: String): LiveData<Result<List<AssignmentResponse>, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = elomateApiService.getToDoList(token)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
    }

    fun getPhaseUser(token: String): LiveData<Result<List<PhaseResponse>, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val phaseItems = elomateApiService.getPhaseUser(token)
                emit(Result.Success(phaseItems))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
    }

    fun getTopicUser(token: String, phaseId: Int): LiveData<Result<List<TopicResponse>, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val topicItems = elomateApiService.getTopicByPhaseId(token, phaseId)
                emit(Result.Success(topicItems))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
    }

    fun getCourseByPhaseIdTopicId(token: String, phaseId: Int, topicId: Int): LiveData<Result<List<CourseResponse>, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val courseItems = elomateApiService.getCourseByPhaseIdTopicId(token, phaseId, topicId)
                emit(Result.Success(courseItems))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
    }

    fun getCourseById(token: String, courseId: Int): LiveData<Result<List<CourseResponse>, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val courseItem = elomateApiService.getCourseById(token, courseId)
                emit(Result.Success(courseItem))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
    }

    fun getAllCourses(token: String): LiveData<Result<List<CourseResponse>, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val courseItems = elomateApiService.getAllCoursesUser(token) // Menghapus CourseResponse karena sudah langsung array
                emit(Result.Success(courseItems))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
    }

    fun getPreActivityByCourseId(token: String, courseId: Int): LiveData<Result<List<PreActivityResponse>, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val preActivityItems = elomateApiService.getPreActivityByCourseId(token, courseId)
                emit(Result.Success(preActivityItems))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
    }

    fun getPostActivityByCourseId(token: String, courseId: Int): LiveData<Result<PostActivityResponse, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val postActivityItems = elomateApiService.getPostActivityByCourseId(token, courseId)
                emit(Result.Success(postActivityItems))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
    }

    fun getPreReadingByCourseId(token: String, courseId: Int): LiveData<Result<List<PreReadingResponse>, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val preReadingItems = elomateApiService.getPreReadingByCourseId(token, courseId)
                emit(Result.Success(preReadingItems))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
    }

    fun getDetailPreReading(token: String, preReadingId: Int): LiveData<Result<PreReadingResponse, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val preReadingItem = elomateApiService.getDetailPreReading(token, preReadingId)
                emit(Result.Success(preReadingItem))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
    }

    fun getDetailAssignment(token: String, assignmentId: Int): LiveData<Result<AssignmentResponse, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val assignmentItem = elomateApiService.getDetailAssignment(token, assignmentId)
                emit(Result.Success(assignmentItem))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
    }

    fun createMentoring(token: String, namaCourse: String, tanggalMentoring: String, jamMulai: String, jamSelesai: String, metodeMentoring: String, tipeMentoring: String): LiveData<Result<SuccessResponse, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val createMentoringRequest = MentoringRequest(namaCourse, tanggalMentoring, jamMulai, jamSelesai, metodeMentoring, tipeMentoring)
                val response = elomateApiService.createMentoring(token, createMentoringRequest)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e(TAG, "Error Create Mentoring API : $errorBody")
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                val errorMessage = messageErrorResponse.message
                emit(Result.Error(MessageErrorResponse(errorMessage)))
            }
        }

    fun getMethodMentoring(token: String): LiveData<Result<List<String>, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = elomateApiService.getMethodMentoring(token)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
        }

    fun getTypeMentoring(token: String): LiveData<Result<List<String>, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = elomateApiService.getTypeMentoring(token)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
        }

    fun getUpcomingMentoring(token: String): LiveData<Result<List<MentoringResponse>, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = elomateApiService.getUpcomingMentoring(token)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
        }

    fun getFeedbackMentoring(token: String): LiveData<Result<List<MentoringResponse>, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = elomateApiService.getFeedbackMentoring(token)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
        }

    fun getApproveMentoring(token: String): LiveData<Result<List<MentoringResponse>, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = elomateApiService.getApproveMentoring(token)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
        }

    fun getDetailMentoring(token: String, mentoringId: Int): LiveData<Result<MentoringResponse, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = elomateApiService.getDetailMentoring(token, mentoringId)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
        }

    fun getReportByPhaseIdTopicId(token: String, phaseId: Int, topicId: Int): LiveData<Result<ReportResponse, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val reportItems = elomateApiService.getReportByPhaseIdTopicId(token, phaseId, topicId)
                emit(Result.Success(reportItems))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
        }

    fun getAssessment(token: String): LiveData<Result<List<AssessmentResponse>, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = elomateApiService.getAssessment(token)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
        }

    fun getSelfAssessment(token: String): LiveData<Result<List<AssessmentsItem>, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = elomateApiService.getSelfAssessment(token)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
        }

    fun getPeerAssessment(token: String): LiveData<Result<List<AssessmentsItem>, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = elomateApiService.getPeerAssessment(token)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
        }

    fun getQuestionAssessment(token: String, assessmentId: Int): LiveData<Result<QuestionAssessmentResponse, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = elomateApiService.getQuestionAssessment(token, assessmentId)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
        }

    fun getListPeerAssessment(token: String, assessmentId: Int): LiveData<Result<PeerAssessmentResponse, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = elomateApiService.getPeerAssessmentList(token, assessmentId)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse = Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
        }

    fun postAnswerSelfAssessment(token: String, assessmentId: Int, answer: List<AnswerSelfAssessmentRequest>): LiveData<Result<SuccessResponse, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = elomateApiService.postSelfAssessmentAnswer(token, assessmentId, answer)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse =
                    Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
        }

    fun postAnswerPeerAssessment(token: String, assessmentId: Int, peerId: Int, answer: List<AnswerSelfAssessmentRequest>): LiveData<Result<SuccessResponse, MessageErrorResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = elomateApiService.postPeerAssessmentAnswer(token, assessmentId, peerId, answer)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val messageErrorResponse =
                    Gson().fromJson(errorBody, MessageErrorResponse::class.java)
                emit(Result.Error(messageErrorResponse))
            } catch (e: SocketTimeoutException) {
                emit(Result.Error(MessageErrorResponse(e.message ?: "Unknown error")))
            }
        }



}