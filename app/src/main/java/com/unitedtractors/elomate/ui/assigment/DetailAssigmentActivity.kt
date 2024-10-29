package com.unitedtractors.elomate.ui.assigment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.databinding.ActivityDetailAssigmentBinding
import com.unitedtractors.elomate.databinding.ActivitySelfAssessmentBinding
import com.unitedtractors.elomate.ui.profile.self_peer.QuestionPeerAssessmentActivity

class DetailAssigmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailAssigmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_assigment)

        binding = ActivityDetailAssigmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            val intent = Intent(this@DetailAssigmentActivity, MultipleChoiceActivity::class.java)
            startActivity(intent)
        }

        // Set click listener for the back button
        findViewById<View>(R.id.ic_back).setOnClickListener {
            finish() // Kembali ke halaman sebelumnya
        }
    }
}