package com.unitedtractors.elomate.ui.profile.changepassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.unitedtractors.elomate.MainActivity
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.databinding.ActivityChangePasswordBinding
import com.unitedtractors.elomate.ui.ViewModelFactory

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding

    private val viewModel by viewModels<ChangePasswordViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        userPreference = UserPreference(this)
        userModel = userPreference.getUser()

        binding.apply {
            icBack.setOnClickListener {
                finish()
            }
            btnSave.setOnClickListener {
                saveChangesPass()
            }
        }
    }

    private fun saveChangesPass() {
        val etCurrentPass = binding.etCurrentPassword.text
        val etNewPass = binding.etNewPassword.text
        val etConfirmNewPass = binding.etConfirmPassword.text

        if (etCurrentPass!!.isEmpty() || etNewPass!!.isEmpty() || etConfirmNewPass!!.isEmpty()) {
            Toast.makeText(this, "Silahkan isi kolom yang ada", Toast.LENGTH_SHORT).show()
        } else if (etNewPass.length < 8 || etConfirmNewPass.length < 8) {
            Toast.makeText(this, "Password minimal 8 karakter", Toast.LENGTH_SHORT).show()
        } else if (etNewPass.toString() != etConfirmNewPass.toString()) {
            Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.updateChangePassword(
                "Bearer ${userModel.id}",
                etCurrentPass.toString(),
                etConfirmNewPass.toString()
            ).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> { }
                        is Result.Success -> {
                            Toast.makeText(this, "Berhasil mengubah password", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        is Result.Error -> {
                            Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}