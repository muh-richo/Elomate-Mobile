package com.unitedtractors.elomate.ui.assigment.preactivity.prereading

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.databinding.ActivityDetailPreReadingBinding
import com.unitedtractors.elomate.ui.ViewModelFactory

class DetailPreReadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPreReadingBinding

    private val viewModel by viewModels<PreReadingViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailPreReadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300) // Replace with your color resource

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        userPreference = UserPreference(this)
        userModel = userPreference.getUser()

        val preReadingId = intent.getIntExtra("PRE_READING_ID", -1)
        if (preReadingId != -1) {
            loadDetailPreReading("Bearer ${userModel.id}", preReadingId)
        }



        binding.icBack.setOnClickListener {
            finish()
        }

    }
    private fun loadDetailPreReading(token: String, preReadingId: Int) {
        viewModel.getDetailPreReading(token, preReadingId).observe(this) { result ->
            when (result) {
                is Result.Loading -> { }
                is Result.Success -> {
                    val preReading = result.data

                    binding.tvPreReadingTitle.text = preReading.titleMateri
                    binding.tvContent.text = preReading.kontenMateri
                    binding.tvDescription.text = preReading.descriptionMateri
                }
                is Result.Error -> {
                    Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
