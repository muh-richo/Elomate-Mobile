package com.unitedtractors.elomate.ui.profile.assessment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.unitedtractors.elomate.R

class QuestionPeerAssessmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_peer_assessment)

        // Set click listener for the back button
        findViewById<View>(R.id.ic_back).setOnClickListener {
            finish() // Kembali ke halaman sebelumnya
        }
    }
}