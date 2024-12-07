package com.unitedtractors.elomate.ui.assessment

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.databinding.ActivityAssessmentBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.assessment.peer.PeerAssessmentActivity
import com.unitedtractors.elomate.ui.assessment.self.SelfAssessmentActivity

class AssessmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAssessmentBinding

    private val viewModel by viewModels<AssessmentViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAssessmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        userPreference = UserPreference(this)
        userModel = userPreference.getUser()

        binding.btnSelf.setOnClickListener {
            val intent = Intent(this@AssessmentActivity, SelfAssessmentActivity::class.java)
            startActivity(intent)
        }

        binding.btnPeer.setOnClickListener {
            val intent = Intent(this@AssessmentActivity, PeerAssessmentActivity::class.java)
//            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            startActivity(intent)
        }

        binding.icBack.setOnClickListener{
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        getAssessment()
    }

    private fun getAssessment() {
        viewModel.getAssessment("Bearer ${userModel.id}").observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {  }
                    is Result.Success -> {
                        val response = result.data

                        response.let { assessmentsId ->
                            for (assessment in assessmentsId) {
                                assessment.let { item ->
                                    if (item.category == "Self Assessment") {
                                        binding.tvSelfStatus.text = item.statusTotal
                                        if (item.statusTotal == "Complete") {
                                            binding.ivSelfStatus.setColorFilter(ContextCompat.getColor(binding.root.context, R.color.success_900))
                                            binding.tvSelfStatus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.success_900))
                                        }
                                    } else if (item.category == "Peer Assessment") {
                                        binding.tvPeerStatus.text = item.statusTotal
                                        if (item.statusTotal == "Complete") {
                                            binding.ivSelfStatus.setColorFilter(ContextCompat.getColor(binding.root.context, R.color.success_900))
                                            binding.tvSelfStatus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.success_900))
                                        }
                                    }
                                }
                            }
                        }
                    }
                    is Result.Error -> {
                        Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
