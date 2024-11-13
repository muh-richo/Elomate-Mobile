package com.unitedtractors.elomate.ui.assessment

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.databinding.ActivityAssessmentBinding

class AssessmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAssessmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAssessmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Change the status bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300) // Replace with your color resource

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        binding.btnPeer.setOnClickListener {
            val intent = Intent(this@AssessmentActivity, PeerAssessmentActivity::class.java)
//            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            startActivity(intent)
        }

        binding.btnSelf.setOnClickListener {
            val intent = Intent(this@AssessmentActivity, SelfAssessmentActivity::class.java)
            startActivity(intent)
        }

        binding.icBack.setOnClickListener{
            finish()
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
