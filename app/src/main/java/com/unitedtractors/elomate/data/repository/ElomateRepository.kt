package com.unitedtractors.elomate.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.unitedtractors.elomate.data.network.response.MessageErrorResponse
import com.unitedtractors.elomate.data.network.response.TokenResponse
import com.unitedtractors.elomate.data.network.retrofit.ElomateApiService
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.response.CourseResponse
import com.unitedtractors.elomate.data.network.response.LoginRequest
import com.unitedtractors.elomate.data.network.response.PhaseResponse
import com.unitedtractors.elomate.data.network.response.PreActivityResponse
import com.unitedtractors.elomate.data.network.response.TopicResponse
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

    fun getPhaseUser(token: String): LiveData<Result<List<PhaseResponse>, MessageErrorResponse>> = liveData {
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

    fun getTopicUser(token: String, phaseId: Int): LiveData<Result<List<TopicResponse>, MessageErrorResponse>> = liveData {
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

    fun getCourseByPhaseIdTopicId(token: String, phaseId: Int, topicId: Int): LiveData<Result<List<CourseResponse>, MessageErrorResponse>> = liveData {
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

    fun getCourseById(token: String, courseId: Int): LiveData<Result<List<CourseResponse>, MessageErrorResponse>> = liveData {
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

    fun getAllCourses(token: String): LiveData<Result<List<CourseResponse>, MessageErrorResponse>> = liveData {
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

    fun getPreActivityByCourseId(token: String, courseId: Int): LiveData<Result<List<PreActivityResponse>, MessageErrorResponse>> = liveData {
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

}