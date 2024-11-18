package com.unitedtractors.elomate.ui.assigment.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.databinding.ActivityDetailAssigmentBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.assigment.question.MultipleChoiceActivity

class DetailAssignmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailAssigmentBinding

    private val viewModel by viewModels<DetailAssignmentViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailAssigmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300) // Replace with your color resource

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        userPreference = UserPreference(this)
        userModel = userPreference.getUser()

        val assignmentId = intent.getIntExtra("ASSIGNMENT_ID", -1)
        if (assignmentId != -1) {
            loadAssignmentDetails("Bearer ${userModel.id}", assignmentId)
        }


        binding.btnStart.setOnClickListener {
            val intent = Intent(this@DetailAssignmentActivity, MultipleChoiceActivity::class.java)
            startActivity(intent)
        }

        binding.icBack.setOnClickListener {
            finish()
        }
    }

    private fun loadAssignmentDetails(token: String, assignmentId: Int) {
        viewModel.getDetailAssignment(token, assignmentId).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    // Handle loading state
                }
                is Result.Success -> {
                    val assignment = result.data

                    binding.tvTitleAssignment.text = assignment.title
                    binding.tvStartDate.text = assignment.tanggalMulai
                    binding.tvEndDate.text = assignment.tanggalSelesai
                    binding.tvScore.text = assignment.active
                    binding.tvType.text = assignment.questionType
//                    binding.tvTotalQuestion.text =
                }
                is Result.Error -> {
                    Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}