package com.unitedtractors.elomate.ui.profile.assessment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.databinding.ActivitySelfAssessmentBinding

class SelfAssessmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelfAssessmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelfAssessmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardSelf.setOnClickListener {
            val intent = Intent(this@SelfAssessmentActivity, QuestionPeerAssessmentActivity::class.java)
            startActivity(intent)
        }

        // Set click listener for the back button
        findViewById<View>(R.id.ic_back).setOnClickListener {
            finish() // Kembali ke halaman sebelumnya
        }
    }
}