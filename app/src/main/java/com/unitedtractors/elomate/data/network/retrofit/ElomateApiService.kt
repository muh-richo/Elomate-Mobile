package com.unitedtractors.elomate.data.network.retrofit

import com.unitedtractors.elomate.data.network.response.CourseResponse
import com.unitedtractors.elomate.data.network.response.CourseResponseItem
import com.unitedtractors.elomate.data.network.response.LoginRequest
import com.unitedtractors.elomate.data.network.response.TokenResponse
import com.unitedtractors.elomate.data.network.response.UserResponse
import retrofit2.http.*

interface ElomateApiService {

//    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
//        @Field("email") email: String,
//        @Field("password") password: String,

        @Body loginRequest: LoginRequest
    ): TokenResponse

    @GET("auth/me")
    suspend fun getCurrentUser(
        @Header("Authorization") token: String
    ): UserResponse

    @GET("courses")
    suspend fun getCourses(
        @Header("Authorization") token: String
    ): List<CourseResponseItem> // Mengubah dari CourseResponse ke List<CourseResponseItem>
}
