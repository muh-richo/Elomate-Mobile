package com.unitedtractors.elomate.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.databinding.ActivityAnnouncementBinding
import com.unitedtractors.elomate.databinding.ActivityMainBinding

class AnnouncementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnnouncementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAnnouncementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Change the status bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300) // Replace with your color resource

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        // Set click listener for the back button
        binding.icBack.setOnClickListener {
            finish()
        }
    }
}