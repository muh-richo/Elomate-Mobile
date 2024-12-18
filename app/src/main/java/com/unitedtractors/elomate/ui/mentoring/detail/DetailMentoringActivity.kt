package com.unitedtractors.elomate.ui.mentoring.detail

import android.annotation.SuppressLint
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
import com.unitedtractors.elomate.databinding.ActivityDetailMentoringBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.mentoring.MentoringViewModel

class DetailMentoringActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMentoringBinding

    private val viewModel by viewModels<MentoringViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailMentoringBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

    }

    override fun onResume() {
        super.onResume()

        userPreference = UserPreference(this)
        userModel = userPreference.getUser()

        val mentoringId = intent.getIntExtra("MENTORING_ID", -1)
        if (mentoringId != -1) {
            loadDetailMentoring(mentoringId)
        }

        binding.apply {
            icBack.setOnClickListener {
                finish()
            }
            btnSave.setOnClickListener {
                submitForm(mentoringId)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadDetailMentoring(mentoringId: Int) {
        viewModel.getDetailMentoring("Bearer ${userModel.token}", mentoringId).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {  }
                    is Result.Success -> {
                        val response = result.data

                        if (response.status == "Upcoming") {
                            binding.status.backgroundTintList = ContextCompat.getColorStateList(binding.root.context, R.color.blue_50)
                            binding.tvStatus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.blue_500))
                        } else if (response.status == "Need Revision" || response.status == "Overdue") {
                            binding.status.backgroundTintList = ContextCompat.getColorStateList(binding.root.context, R.color.error_50)
                            binding.tvStatus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.error_500))
                        }else if (response.status == "Need Approval") {
                            binding.status.backgroundTintList = ContextCompat.getColorStateList(binding.root.context, R.color.blue_50)
                            binding.tvStatus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.blue_500))
                        } else if (response.status == "Approve") {
                            binding.etLessonLearned.isEnabled = false
                            binding.etCatatanMentor.isEnabled = false
                            binding.btnSave.visibility = View.GONE
                            binding.status.backgroundTintList = ContextCompat.getColorStateList(binding.root.context, R.color.success_50)
                            binding.tvStatus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.success_900))
                        }

                        binding.tvStatus.text = response.status
                        binding.tvMentor.text = response.namaFasilitator
                        binding.tvTopik.text = response.namaTopik
                        binding.tvType.text = response.tipeMentoring
                        binding.tvDate.text = response.tanggalMentoring
                        binding.tvTime.text = "${response.waktuMentoring } WIB"
                        binding.tvMethod.text = response.metodeMentoring

                        binding.tvKompetensi.text = response.kompetensiYangDievaluasi
                        binding.etLessonLearned.setText(response.lessonLearnedCompetencies)
                        binding.etCatatanMentor.setText(response.catatanMentor)

                    }
                    is Result.Error -> {
                        Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun submitForm(mentoringId: Int) {
        val etLessonLearned = binding.etLessonLearned.text
        val etCatatanMentor = binding.etCatatanMentor.text

        if (etLessonLearned!!.isEmpty() && etCatatanMentor!!.isEmpty()) {
            Toast.makeText(this, "Silahkan isi kolom yang ada", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.submitFormFeedback(
                "Bearer ${userModel.token}",
                mentoringId,
                binding.etLessonLearned.text.toString(),
                binding.etCatatanMentor.text.toString(),
            ).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {  }
                        is Result.Success -> {
                            Toast.makeText(this, "Berhasil menyimpan data", Toast.LENGTH_SHORT).show()
                        }
                        is Result.Error -> {
                            Toast.makeText(this, "Mentoring gagal ditambahkan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}