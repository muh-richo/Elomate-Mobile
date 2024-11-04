package com.unitedtractors.elomate.ui.profile.forum

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.databinding.ActivityAddForumBinding

class AddForumActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddForumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddForumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Change the status bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300) // Replace with your color resource

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        binding.btnPost.setOnClickListener {
            val intent = Intent(this@AddForumActivity, DetailForumActivity::class.java)
            startActivity(intent)
        }
    }
}