package com.unitedtractors.elomate.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.unitedtractors.elomate.R

class ToDoListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)

        // Set click listener for the back button
        findViewById<View>(R.id.ic_back).setOnClickListener {
            finish() // Kembali ke halaman sebelumnya
        }
    }
}