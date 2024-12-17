package com.unitedtractors.elomate.ui.profile.participant.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.adapter.EducationAdapter
import com.unitedtractors.elomate.adapter.ParticipantEducationAdapter
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.databinding.ActivityDetailParticipantBinding
import com.unitedtractors.elomate.databinding.ActivityEducationBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.profile.participant.ParticipantDataViewModel
import com.unitedtractors.elomate.ui.profile.riwayatpendidikan.EducationViewModel

class DetailParticipantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailParticipantBinding

    private val viewModel by viewModels<ParticipantDataViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailParticipantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        userPreference = UserPreference(this)
        userModel = userPreference.getUser()

        val userId = intent.getIntExtra("USER_ID", -1)
        loadDetailParticipant(userId)

        binding.apply {
            icBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun loadDetailParticipant(userId: Int){
        viewModel.getParticipantEducation("Bearer ${userModel.token}",userId).observe(this) { result ->
            when (result) {
                is Result.Loading -> {  }
                is Result.Success -> {
                    val response = result.data

                    val userName = response.first().namaLengkap
                    binding.tvNameUser.text = userName

                    val adapter = ParticipantEducationAdapter(response)
                    binding.rvEducation.adapter = adapter
                    binding.rvEducation.layoutManager = LinearLayoutManager(this)
                }
                is Result.Error -> {
                    Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}