package com.unitedtractors.elomate.ui.profile.editprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.unitedtractors.elomate.databinding.ActivityEditProfileBinding
import com.unitedtractors.elomate.ui.ViewModelFactory

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    private val viewModel by viewModels<EditProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        userPreference = UserPreference(this)
        userModel = userPreference.getUser()

        getCurrentUserApi()

        binding.apply {
            icBack.setOnClickListener{
                finish()
            }
            btnSave.setOnClickListener {
                saveChangesEdit()
            }
        }

    }

    private fun getCurrentUserApi() {
        viewModel.getCurrentUserApi("Bearer ${userModel.id}").observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> { }
                    is Result.Success -> {
                        val userResponse = result.data

                        binding.apply {
                            etNamaLengkap.setText(userResponse.namaLengkap)
                            etNrp.setText(userResponse.nrp)
                            etEmail.setText(userResponse.email)
                            etPosisi.setText(userResponse.posisi)
                            etDomisili.setText(userResponse.domisili)
                            etTempatLahir.setText(userResponse.tempatLahir)
                            etTanggalLahir.setText(userResponse.tanggalLahir)
                            etNoHp.setText(userResponse.noHp)
                        }
                    }
                    is Result.Error -> {
                        Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun saveChangesEdit() {
        val etDomisili = binding.etDomisili.text
        val etTempatLahir = binding.etTempatLahir.text
        val etTanggalLahir = binding.etTanggalLahir.text
        val etNoHp = binding.etNoHp.text

        if (etDomisili!!.isEmpty() || etTempatLahir!!.isEmpty() || etTanggalLahir!!.isEmpty() || etNoHp!!.isEmpty()) {
            Toast.makeText(this, "Silahkan isi kolom yang ada", Toast.LENGTH_SHORT).show()
        } else {

            viewModel.updateProfile(
                "Bearer ${userModel.id}",
                etDomisili.toString(),
                etTempatLahir.toString(),
                etTanggalLahir.toString(),
                etNoHp.toString()
            ).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> { }
                        is Result.Success -> {
                            Toast.makeText(this, "Profile berhasil diupdate", Toast.LENGTH_SHORT).show()

                            setResult(RESULT_OK)
                            finish()
                        }
                        is Result.Error -> {
                            Toast.makeText(this, "Update profile gagal", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

    }
}