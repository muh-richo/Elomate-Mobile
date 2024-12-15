package com.unitedtractors.elomate.ui.assessment.question

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
import com.unitedtractors.elomate.adapter.QuestionAssessmentAdapter
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.databinding.ActivityQuestionAssessmentBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.assessment.AssessmentViewModel

class QuestionAssessmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionAssessmentBinding

    private val viewModel by viewModels<AssessmentViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityQuestionAssessmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        userPreference = UserPreference(this)
        userModel = userPreference.getUser()

        val assessmentTitle = intent.getStringExtra("ASSESSMENT_TITLE")
        val assessmentType = intent.getStringExtra("ASSESSMENT_TYPE")
        val assessmentId = intent.getIntExtra("ASSESSMENT_ID", -1)
        val peerId = intent.getIntExtra("PEER_ID", -1)
        if (assessmentId != -1) {
            loadQuestions(assessmentId)

            if (assessmentType == "Self Assessment") {
                binding.btnSubmit.setOnClickListener {
                    submitSelfAssessment("Bearer ${userModel.token}", assessmentId)
                }
            } else if (assessmentType == "Peer Assessment") {
                binding.btnSubmit.setOnClickListener {
                    submitPeerAssessment("Bearer ${userModel.token}", assessmentId, peerId)
                }
            }
        }


        binding.apply {
            icBack.setOnClickListener {
                finish()
            }
            tvTitleAssessment.text = assessmentTitle
        }

    }

    private fun loadQuestions(assessmentId : Int) {
        binding.rvQuestionAssessment.layoutManager = LinearLayoutManager(this)

        viewModel.getQuestionAssessment("Bearer ${userModel.token}", assessmentId).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE

                        val questionData = result.data.question
                        val adapter = QuestionAssessmentAdapter(questionData)
                        binding.rvQuestionAssessment.adapter = adapter

                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnSubmit.visibility = View.GONE
                        Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun submitSelfAssessment(token: String, assessmentId: Int) {
        val adapter = binding.rvQuestionAssessment.adapter as? QuestionAssessmentAdapter
        val answers = adapter?.getAnswers()

        if (answers.isNullOrEmpty()) {
            Toast.makeText(this, "Harap isi semua pertanyaan!", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.postAnswerSelfAssessment(token, assessmentId, answers).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Jawaban berhasil dikirim!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Gagal mengirim jawaban: ${result.error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun submitPeerAssessment(token: String, assessmentId: Int, peerId: Int) {
        val adapter = binding.rvQuestionAssessment.adapter as? QuestionAssessmentAdapter
        val answers = adapter?.getAnswers()

        if (answers.isNullOrEmpty()) {
            Toast.makeText(this, "Harap isi semua pertanyaan!", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.postAnswerPeerAssessment(token, assessmentId, peerId, answers).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Jawaban berhasil dikirim!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Gagal mengirim jawaban: ${result.error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}