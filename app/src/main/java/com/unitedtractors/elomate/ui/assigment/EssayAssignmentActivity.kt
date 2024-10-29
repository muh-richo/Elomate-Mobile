package com.unitedtractors.elomate.ui.assigment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.unitedtractors.elomate.R

class EssayAssignmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_essay_assignment)

        // Set click listener for the back button
        findViewById<View>(R.id.btn_close).setOnClickListener {
            finish() // Kembali ke halaman sebelumnya
        }
    }
}