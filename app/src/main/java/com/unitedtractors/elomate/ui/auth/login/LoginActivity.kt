package com.unitedtractors.elomate.ui.auth.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.unitedtractors.elomate.MainActivity
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.databinding.ActivityLoginBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.auth.forgotpassword.ForgotPasswordActivity
import com.unitedtractors.elomate.utils.Utils
import com.unitedtractors.elomate.data.network.Result

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userPreference: UserPreference

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize UserPreference
        userPreference = UserPreference(this)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        // Check if user is already logged in
        if (userPreference.getAuthToken() != null) {
            // Auth token exists, navigate to MainActivity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        binding.apply {
            btnForgotPassword.setOnClickListener {
                val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                startActivity(intent)
            }
            btnLogin.setOnClickListener {
                login()
            }
        }
    }

    private fun login() {
        val etLoginEmail = binding.etLoginEmail.text
        val etLoginPassword = binding.etLoginPassword.text

        if (etLoginEmail!!.isEmpty() || etLoginPassword!!.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        } else if (!Utils.isValidEmail(etLoginEmail.toString()) || etLoginPassword.length < 8) {
            Toast.makeText(this, "Please check your email and password", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.login(
                etLoginEmail.toString(),
                etLoginPassword.toString()
            ).observe(this) { result ->
                when (result) {
                    is Result.Loading -> { /* Show loading if necessary */ }

                    is Result.Success -> {
                        val response = result.data
                        userPreference.saveAuthToken(response.token) // Save token to SharedPreferences
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }

                    is Result.Error -> {
                        Toast.makeText(this, result.error.message ?: "An error occurred", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    // Fungsi untuk menyimpan token ke SharedPreferences
//    private fun saveAuthToken(token: String) {
//        val sharedPreferences = getSharedPreferences("YourPreferenceName", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.putString("authToken", token)
//        editor.apply()
//    }
}
