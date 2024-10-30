package com.unitedtractors.elomate.ui.profile.forum

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.databinding.ActivityForumBinding

class ForumActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityForumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabAddForum.setOnClickListener{
            val intent = Intent(this@ForumActivity, AddForumActivity::class.java)
            startActivity(intent)
        }
    }
}