package com.unitedtractors.elomate.ui.profile.forum

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.databinding.ActivityDetailForumBinding

class DetailForumActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailForumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        enableEdgeToEdge()
        binding = ActivityDetailForumBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}