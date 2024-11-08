package com.unitedtractors.elomate.data.network.retrofit

import com.unitedtractors.elomate.data.network.response.LoginRequest
import com.unitedtractors.elomate.data.network.response.LoginResponse
import retrofit2.http.*

interface ElomateApiService {

//    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(
//        @Field("email") email: String,
//        @Field("password") password: String,

        @Body loginRequest: LoginRequest
    ): LoginResponse
}
