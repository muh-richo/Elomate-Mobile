package com.unitedtractors.elomate.ui.profile.self_peer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.databinding.ActivityPeerAssessmentBinding
import com.unitedtractors.elomate.databinding.ActivitySelfPeerBinding

class PeerAssessmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPeerAssessmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeerAssessmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardSelf.setOnClickListener {
            val intent = Intent(this@PeerAssessmentActivity, ListPeerAssessmentActivity::class.java)
            startActivity(intent)
        }

        binding.icBack.setOnClickListener {
            finish() // Kembali ke halaman sebelumnya
        }
    }
}