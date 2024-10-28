package com.unitedtractors.elomate.ui.profile.self_peer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.unitedtractors.elomate.R

class ListPeerAssessmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_peer_assessment)

        findViewById<View>(R.id.ic_back).setOnClickListener {
            finish() // Kembali ke halaman sebelumnya
        }
    }
}