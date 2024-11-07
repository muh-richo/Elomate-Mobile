package com.unitedtractors.elomate.data.retrofit

import com.unitedtractors.elomate.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
//    @GET("users/email/{user_email}")
//    fun getUser(
//        @Path("user_email") id: String
//    ): Call<UserResponse>

    @FormUrlEncoded
    @POST("users/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserResponse>


}
