package com.unitedtractors.elomate.ui.assessment.peer

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
import com.unitedtractors.elomate.adapter.PeerAssessmentAdapter
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.databinding.ActivityListPeerAssessmentBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.assessment.AssessmentViewModel
import com.unitedtractors.elomate.ui.assessment.question.QuestionAssessmentActivity

class ListPeerAssessmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListPeerAssessmentBinding

    private val viewModel by viewModels<AssessmentViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListPeerAssessmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        userPreference = UserPreference(this)
        userModel = userPreference.getUser()

        binding.icBack.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        val assessmentTitle = intent.getStringExtra("ASSESSMENT_TITLE")
        val assessmentType = intent.getStringExtra("ASSESSMENT_TYPE")
        val assessmentId = intent.getIntExtra("ASSESSMENT_ID", -1)
        if (assessmentTitle != null && assessmentType != null && assessmentId != -1) {
            loadListPeer(assessmentTitle, assessmentType, assessmentId)
        }
    }

    private fun loadListPeer(assessmentTitle: String, assessmentType: String, assessmentId: Int) {
        binding.rvListPeer.layoutManager = LinearLayoutManager(this)

        viewModel.getListPeerAssessment("Bearer ${userModel.token}", assessmentId).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE

                        val listPeer = result.data.data
                        val adapter = PeerAssessmentAdapter(listPeer) { peerId, status ->
                            val intent = Intent(this, QuestionAssessmentActivity::class.java)
                            intent.putExtra("ASSESSMENT_TITLE", assessmentTitle)
                            intent.putExtra("ASSESSMENT_TYPE", assessmentType)
                            intent.putExtra("ASSESSMENT_ID", assessmentId)
                            intent.putExtra("ASSESSMENT_STATUS", status)
                            intent.putExtra("PEER_ID", peerId)
                            startActivity(intent)
                        }
                        binding.rvListPeer.adapter = adapter
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