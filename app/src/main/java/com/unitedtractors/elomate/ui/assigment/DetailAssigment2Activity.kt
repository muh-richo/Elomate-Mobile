package com.unitedtractors.elomate.ui.assigment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.databinding.ActivityDetailAssigment2Binding

class DetailAssigment2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailAssigment2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_assigment)

        binding = ActivityDetailAssigment2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            val intent = Intent(this@DetailAssigment2Activity, EssayAssignmentActivity::class.java)
            startActivity(intent)
        }

        // Set click listener for the back button
        findViewById<View>(R.id.ic_back).setOnClickListener {
            finish() // Kembali ke halaman sebelumnya
        }
    }
}