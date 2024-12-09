package com.unitedtractors.elomate.ui.assigment.question

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.adapter.AnswerOptionAdapter
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.request.AnswerMultipleChoiceRequest
import com.unitedtractors.elomate.data.network.response.QuestionResponse
import com.unitedtractors.elomate.databinding.ActivityMultipleChoiceBinding
import com.unitedtractors.elomate.ui.ViewModelFactory

class MultipleChoiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMultipleChoiceBinding

    private val viewModel by viewModels<QuestionViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    private var questionList: List<QuestionResponse> = emptyList() // Daftar pertanyaan
    private var currentQuestionIndex: Int = 0 // Indeks pertanyaan saat ini
    private val selectedAnswers = mutableMapOf<Int, String>() // Jawaban yang dipilih

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMultipleChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        userPreference = UserPreference(this)
        userModel = userPreference.getUser()

        val assignmentId = intent.getIntExtra("ASSIGNMENT_ID", -1)
        if (assignmentId != -1) {
            loadQuestions(assignmentId)
        }

        binding.btnNext.setOnClickListener {
            if (currentQuestionIndex < questionList.size - 1) {
                currentQuestionIndex++
                displayQuestion()
            } else {
                submitAnswers(assignmentId)
            }
        }

        binding.btnPrevious.setOnClickListener {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--
                displayQuestion()
            }
        }

        binding.icClose.setOnClickListener {
            finish()
        }
    }

    private fun loadQuestions(assignmentId: Int) {
        viewModel.getQuestion("Bearer ${userModel.id}", assignmentId).observe(this) { result ->
            when (result) {
                is Result.Loading -> {  }
                is Result.Success -> {
                    questionList = result.data
                    displayQuestion()
                }
                is Result.Error -> {
                    Toast.makeText(this, "Gagal memuat pertanyaan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayQuestion() {
        if (questionList.isNotEmpty() && currentQuestionIndex in questionList.indices) {
            val currentQuestion = questionList[currentQuestionIndex]

            binding.tvQuestion.text = currentQuestion.questionText

            // Setup RecyclerView untuk pilihan jawaban
            val adapter = AnswerOptionAdapter(
                options = currentQuestion.allOptions.orEmpty(),
                questionId = currentQuestion.questionId ?: 0,
                selectedAnswers = selectedAnswers // Teruskan selectedAnswers ke adapter
            ) { selectedOption ->
                selectedAnswers[currentQuestion.questionId ?: 0] = selectedOption
            }
            binding.rvPilihanJawaban.layoutManager = LinearLayoutManager(this)
            binding.rvPilihanJawaban.adapter = adapter

            binding.tvTotalQuestion.text = "Pertanyaan ${currentQuestionIndex + 1}/${questionList.size}"

            binding.btnPrevious.visibility = if (currentQuestionIndex == 0) View.GONE else View.VISIBLE
            binding.tvNext.text = if (currentQuestionIndex == questionList.size - 1) "Submit" else "Next"
        }
    }


    private fun submitAnswers(assignmentId: Int) {
        val answers = selectedAnswers.map { (questionId, selectedOption) ->
            AnswerMultipleChoiceRequest(questionId = questionId, userAnswer = selectedOption)
        }

        viewModel.submitAnswerMultipleChoice("Bearer ${userModel.id}", assignmentId, answers).observe(this) { result ->
            when (result) {
                is Result.Loading -> {  }
                is Result.Success -> {
                    Toast.makeText(this, "Jawaban berhasil dikirim", Toast.LENGTH_SHORT).show()
                    finish() // Tutup activity setelah sukses
                }
                is Result.Error -> {
                    Toast.makeText(this, "Gagal mengirim jawaban: ${result.error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
