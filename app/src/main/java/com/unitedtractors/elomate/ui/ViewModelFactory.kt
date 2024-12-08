package com.unitedtractors.elomate.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.unitedtractors.elomate.data.repository.ElomateRepository
import com.unitedtractors.elomate.di.Injection
import com.unitedtractors.elomate.ui.assessment.AssessmentViewModel
import com.unitedtractors.elomate.ui.assigment.detail.DetailAssignmentViewModel
import com.unitedtractors.elomate.ui.assigment.postactivity.PostActivityViewModel
import com.unitedtractors.elomate.ui.assigment.preactivity.activity.PreActivityViewModel
import com.unitedtractors.elomate.ui.assigment.preactivity.prereading.PreReadingViewModel
import com.unitedtractors.elomate.ui.assigment.course.CourseViewModel
import com.unitedtractors.elomate.ui.assigment.question.QuestionViewModel
import com.unitedtractors.elomate.ui.auth.login.LoginViewModel
import com.unitedtractors.elomate.ui.home.HomeViewModel
import com.unitedtractors.elomate.ui.mentoring.MentoringViewModel
import com.unitedtractors.elomate.ui.profile.changepassword.ChangePasswordViewModel
import com.unitedtractors.elomate.ui.profile.displayprofile.ProfileViewModel
import com.unitedtractors.elomate.ui.profile.editprofile.EditProfileViewModel
import com.unitedtractors.elomate.ui.profile.participant.ParticipantDataViewModel
import com.unitedtractors.elomate.ui.profile.riwayatpendidikan.EducationViewModel
import com.unitedtractors.elomate.ui.report.ReportViewModel
import com.unitedtractors.elomate.ui.todo.ToDoViewModel

class ViewModelFactory private constructor(private val elomateRepository: ElomateRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(elomateRepository) as T
            }
            modelClass.isAssignableFrom(EditProfileViewModel::class.java) -> {
                EditProfileViewModel(elomateRepository) as T
            }
            modelClass.isAssignableFrom(ChangePasswordViewModel::class.java) -> {
                ChangePasswordViewModel(elomateRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(elomateRepository) as T
            }
            modelClass.isAssignableFrom(ToDoViewModel::class.java) -> {
                ToDoViewModel(elomateRepository) as T
            }
            modelClass.isAssignableFrom(CourseViewModel::class.java) -> {
                CourseViewModel(elomateRepository) as T
            }
            modelClass.isAssignableFrom(PreActivityViewModel::class.java) -> {
                PreActivityViewModel(elomateRepository) as T
            }
            modelClass.isAssignableFrom(PreReadingViewModel::class.java) -> {
                PreReadingViewModel(elomateRepository) as T
            }
            modelClass.isAssignableFrom(PostActivityViewModel::class.java) -> {
                PostActivityViewModel(elomateRepository) as T
            }
            modelClass.isAssignableFrom(DetailAssignmentViewModel::class.java) -> {
                DetailAssignmentViewModel(elomateRepository) as T
            }
            modelClass.isAssignableFrom(QuestionViewModel::class.java) -> {
                QuestionViewModel(elomateRepository) as T
            }
            modelClass.isAssignableFrom(ReportViewModel::class.java) -> {
                ReportViewModel(elomateRepository) as T
            }
            modelClass.isAssignableFrom(MentoringViewModel::class.java) -> {
                MentoringViewModel(elomateRepository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(elomateRepository) as T
            }
            modelClass.isAssignableFrom(AssessmentViewModel::class.java) -> {
                AssessmentViewModel(elomateRepository) as T
            }
            modelClass.isAssignableFrom(ParticipantDataViewModel::class.java) -> {
                ParticipantDataViewModel(elomateRepository) as T
            }
            modelClass.isAssignableFrom(EducationViewModel::class.java) -> {
                EducationViewModel(elomateRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}