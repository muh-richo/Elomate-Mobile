package com.unitedtractors.elomate.data.retrofit

import com.unitedtractors.elomate.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

data class LoginRequest(
    val email: String,
    val password: String
)

interface ApiService {

    @POST("users/login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<UserResponse>
}
