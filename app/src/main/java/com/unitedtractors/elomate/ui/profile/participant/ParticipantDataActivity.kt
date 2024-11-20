package com.unitedtractors.elomate.ui.profile.participant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.adapter.ParticipantAdapter
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.response.UserResponse
import com.unitedtractors.elomate.databinding.ActivityParticipantDataBinding
import com.unitedtractors.elomate.ui.ViewModelFactory

class ParticipantDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParticipantDataBinding

    private val viewModel by viewModels<ParticipantDataViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    private lateinit var participantData: List<UserResponse>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityParticipantDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        userPreference = UserPreference(this)
        userModel = userPreference.getUser()

        loadParticipantData()

        binding.icBack.setOnClickListener {
            finish()
        }
    }

    private fun loadParticipantData() {
        binding.rvParticipantList.layoutManager = LinearLayoutManager(this)

        viewModel.getParticipantData("Bearer ${userModel.id}").observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
                        participantData = result.data

                        val adapter = ParticipantAdapter(participantData)
                        binding.rvParticipantList.adapter = adapter
                    }
                    is Result.Error -> {
                        Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }
}