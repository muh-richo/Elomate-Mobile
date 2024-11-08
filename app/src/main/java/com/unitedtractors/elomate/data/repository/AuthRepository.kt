//package com.unitedtractors.elomate.data.repository
//
//import com.unitedtractors.elomate.data.network.retrofit.ElomateApiService
//
//class AuthRepository(
//    private val api: ElomateApiService
//) : ElomateRepository() {
//
//    suspend fun login(
//        email: String,
//        password: String
//    ) = safeApiCall {
//        api.login(email, password)
//    }
//}