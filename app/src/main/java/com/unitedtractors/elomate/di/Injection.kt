package com.unitedtractors.elomate.di

import android.content.Context
import com.unitedtractors.elomate.data.network.retrofit.ApiConfig
import com.unitedtractors.elomate.data.repository.ElomateRepository

object Injection {
    fun provideRepository(context: Context): ElomateRepository {
        val authApiService = ApiConfig.getElomateApiService()
        return ElomateRepository(authApiService)
    }
}