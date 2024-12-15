package com.unitedtractors.elomate.ui.assessment.self

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.adapter.AssessmentAdapter
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.databinding.ActivitySelfAssessmentBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.assessment.AssessmentViewModel
import com.unitedtractors.elomate.ui.assessment.question.QuestionAssessmentActivity

class SelfAssessmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelfAssessmentBinding

    private val viewModel by viewModels<AssessmentViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySelfAssessmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        userPreference = UserPreference(this)
        userModel = userPreference.getUser()

        loadSelfAssessment()

        binding.icBack.setOnClickListener {
            finish()
        }
    }

    private fun loadSelfAssessment() {
        binding.rvSelfAssessment.layoutManager = LinearLayoutManager(this)

        viewModel.getSelfAssessment("Bearer ${userModel.token}").observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {  }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val response = result.data

                        val adapter = AssessmentAdapter(response) { assessmentId, assessmentTitle ->
                            val intent = Intent(this, QuestionAssessmentActivity::class.java)
                            intent.putExtra("ASSESSMENT_TITLE", assessmentTitle)
                            intent.putExtra("ASSESSMENT_TYPE", "Self Assessment")
                            intent.putExtra("ASSESSMENT_ID", assessmentId)
                            startActivity(intent)
                        }
                        binding.rvSelfAssessment.adapter = adapter
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}