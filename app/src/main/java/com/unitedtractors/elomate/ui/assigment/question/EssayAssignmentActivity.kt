package com.unitedtractors.elomate.ui.assigment.question

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.adapter.QuestionEssayAdapter
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.databinding.ActivityEssayAssignmentBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import java.io.File

class EssayAssignmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEssayAssignmentBinding

    private val viewModel by viewModels<QuestionViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    private val FILE_REQUEST_CODE = 100

    private var selectedFilePath: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEssayAssignmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        userPreference = UserPreference(this)
        userModel = userPreference.getUser()

        val assignmentId = intent.getIntExtra("ASSIGNMENT_ID", -1)
        if (assignmentId != -1) {
            loadQuestions(assignmentId)
        }

        binding.editTextFile.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            startActivityForResult(intent, FILE_REQUEST_CODE)
        }

        binding.btnSubmit.setOnClickListener {
            submitAnswers(assignmentId)
        }

        binding.btnClose.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val uri = data.data // URI file yang dipilih
            val filePath = getFilePathFromUri(uri) // Konversi URI ke path absolut
            if (filePath != null) {
                binding.editTextFile.setText(File(filePath).name) // Tampilkan nama file
                selectedFilePath = filePath // Simpan path file yang dipilih
            } else {
                Toast.makeText(this, "Gagal mengambil file", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getFilePathFromUri(uri: Uri?): String? {
        uri ?: return null
        var filePath: String? = null
        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val columnIndex = cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            val fileName = cursor.getString(columnIndex)
            val file = File(cacheDir, fileName)
            contentResolver.openInputStream(uri)?.use { inputStream ->
                file.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            filePath = file.absolutePath
        }
        return filePath
    }

    private fun loadQuestions(assignmentId: Int) {
        binding.rvQuestionEssay.layoutManager = LinearLayoutManager(this)

        viewModel.getQuestion("Bearer ${userModel.token}", assignmentId).observe(this) { result ->
            when (result) {
                is Result.Loading -> {  }
                is Result.Success -> {
                    val response = result.data

                    val adapter = QuestionEssayAdapter(response)
                    binding.rvQuestionEssay.adapter = adapter
                }
                is Result.Error -> {
                    Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun submitAnswers(assignmentId: Int) {
        val essayAnswer = binding.etAnswer.text.toString()
        if (essayAnswer.isBlank()) {
            Toast.makeText(this, "Jawaban tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedFilePath.isNullOrEmpty()) {
            Toast.makeText(this, "Harap pilih file lampiran", Toast.LENGTH_SHORT).show()
            return
        }

//        binding.progressBar.visibility = View.VISIBLE

        viewModel.submitAnswerEssay(
            token = "Bearer ${userModel.token}",
            assignmentId = assignmentId,
            essayAnswers = essayAnswer,
            filePath = selectedFilePath!!
        ).observe(this) { result ->
            when (result) {
                is Result.Loading -> {  }
                is Result.Success -> {
                    Toast.makeText(this, "Jawaban berhasil dikirim!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is Result.Error -> {
                    Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
