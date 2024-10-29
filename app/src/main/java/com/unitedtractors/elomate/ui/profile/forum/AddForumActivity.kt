package com.unitedtractors.elomate.ui.profile.forum

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
    }
}