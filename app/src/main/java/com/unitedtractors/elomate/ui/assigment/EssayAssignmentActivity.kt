package com.unitedtractors.elomate.ui.assigment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.unitedtractors.elomate.R

class EssayAssignmentActivity : AppCompatActivity() {

    private val FILE_REQUEST_CODE = 100
    private lateinit var editTextFile: TextInputEditText // Declare the view

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_essay_assignment)

        // Initialize the editTextFile using findViewById
        editTextFile = findViewById(R.id.editTextFile)

        // Set click listener to open file picker
        editTextFile.setOnClickListener {
            openFilePicker()
        }

        // Set click listener for the back button
        findViewById<View>(R.id.btn_close).setOnClickListener {
            finish() // Go back to the previous page
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("application/pdf", "application/msword", "image/jpeg", "image/png"))
        startActivityForResult(intent, FILE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedFileUri: Uri? = data?.data
            selectedFileUri?.let {
                editTextFile.setText(it.path) // Display file path in TextInputEditText
                // You can further process the file (upload to server, etc.)
            } ?: Toast.makeText(this, "File not selected!", Toast.LENGTH_SHORT).show()
        }
    }
}
