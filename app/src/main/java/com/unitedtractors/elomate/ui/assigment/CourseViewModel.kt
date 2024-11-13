package com.unitedtractors.elomate.ui.assigment

import androidx.lifecycle.ViewModel
import com.unitedtractors.elomate.data.repository.ElomateRepository

class CourseViewModel(private val repository: ElomateRepository) : ViewModel() {

    fun getCurrentUserApi(token: String) = repository.getCurrentUserApi(token)

    fun getCourses(token: String) = repository.getCourses(token)

}