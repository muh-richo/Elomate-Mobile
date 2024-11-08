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
import com.unitedtractors.elomate.data.network.response.LoginRequest
import com.unitedtractors.elomate.data.network.response.LoginResponse
import retrofit2.HttpException
import java.net.SocketTimeoutException

class ElomateRepository(
    private val elomateApiService: ElomateApiService
) {
    fun loginApi(email: String, password: String): LiveData<Result<LoginResponse, MessageErrorResponse>> =
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
}