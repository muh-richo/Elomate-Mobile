package com.unitedtractors.elomate.ui.assigment.preactivity.prereading

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.databinding.ActivityDetailPreReadingBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class DetailPreReadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPreReadingBinding

    private val viewModel by viewModels<PreReadingViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailPreReadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        userPreference = UserPreference(this)
        userModel = userPreference.getUser()

        val preReadingId = intent.getIntExtra("PRE_READING_ID", -1)
        if (preReadingId != -1) {
            loadDetailPreReading("Bearer ${userModel.token}", preReadingId)
        }



        binding.icBack.setOnClickListener {
            finish()
        }

    }

    private fun loadDetailPreReading(token: String, preReadingId: Int) {
        viewModel.getDetailPreReading(token, preReadingId).observe(this) { result ->
            when (result) {
                is Result.Loading -> { }
                is Result.Success -> {
                    val preReading = result.data

                    binding.tvPreReadingTitle.text = preReading.titleMateri
                    binding.tvDescription.text = preReading.descriptionMateri

                    val pdfUrl = preReading.files?.firstOrNull()?.signedUrl

                    if (pdfUrl != null) {
                        binding.pdfView.visibility = View.VISIBLE

                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val url = URL(pdfUrl)
                                val connection = url.openConnection()
                                connection.connect()

                                // Unduh file PDF
                                val inputStream = connection.getInputStream()
                                val file = File(cacheDir, "temp.pdf")
                                val outputStream = FileOutputStream(file)

                                inputStream.use { input ->
                                    outputStream.use { output ->
                                        input.copyTo(output)
                                    }
                                }

                                withContext(Dispatchers.Main) {
                                    // Tampilkan PDF dari file lokal
                                    binding.pdfView.fromFile(file).load()
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(this@DetailPreReadingActivity, "Gagal memuat file PDF", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } else {
                        Toast.makeText(this, "PDF tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }
                }
                is Result.Error -> {
                    Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
