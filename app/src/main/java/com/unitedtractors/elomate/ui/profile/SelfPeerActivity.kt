package com.unitedtractors.elomate.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.databinding.ActivitySelfPeerBinding

class SelfPeerActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelfPeerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi binding
        binding = ActivitySelfPeerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listener for the back button
        findViewById<View>(R.id.ic_back).setOnClickListener {
            finish() // Kembali ke halaman sebelumnya
        }

        setupSpinner()
    }

    private fun setupSpinner() {
        // Define the array of items
        val phases = arrayOf("Phase 1", "Phase 2", "Phase 3", "Phase 4", "Phase 10")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, phases)

        val topics = arrayOf("General Development", "Orientasi Divisi", "BGMS", "NEOP")
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, topics)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Mengatur adapter ke spinner
        binding.spinnerDropdown.adapter = adapter
        binding.spinnerDropdown2.adapter = adapter2
    }
}
