package com.unitedtractors.elomate.ui.auth.login

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.unitedtractors.elomate.MainActivity
import com.unitedtractors.elomate.databinding.ActivityLoginBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.auth.forgotpassword.ForgotPasswordActivity
import com.unitedtractors.elomate.utils.Utils
import com.unitedtractors.elomate.data.network.Result

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
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

        Log.d(TAG, "Attempting login with email: $etLoginEmail, password: $etLoginPassword")

        if(etLoginEmail!!.isEmpty() || etLoginPassword!!.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        } else if (!Utils.isValidEmail(etLoginEmail.toString()) || etLoginPassword.length < 8) {
            Toast.makeText(this, "Please check your email and password", Toast.LENGTH_SHORT).show()

        } else {
            Log.d(TAG, "Attempting login with email: $etLoginEmail, password: $etLoginPassword")
            viewModel.login(
                etLoginEmail.toString(),
                etLoginPassword.toString()
            ).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
//                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is Result.Success -> {
//                            binding.progressBar.visibility = View.GONE
                            val response = result.data

//                            userModel.id = response.token

//                            if (binding.cbRemember.isChecked) {
//                                userModel.rememberMe = true
//                            }

//                            userPreference.setUser(userModel)

                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                        is Result.Error -> {
//                            binding.progressBar.visibility = View.GONE

                            if (result.error.message == "There is no user with this email address!") {
                                Toast.makeText(this, "There is no user with this email address!", Toast.LENGTH_SHORT).show()
                            }
                            if (result.error.message == "Incorrect password!") {
                                Toast.makeText(this, "Incorrect password!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

}
