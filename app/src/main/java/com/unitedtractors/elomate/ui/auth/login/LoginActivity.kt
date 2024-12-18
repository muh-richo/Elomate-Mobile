package com.unitedtractors.elomate.ui.auth.login

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.unitedtractors.elomate.MainActivity
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
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

    private var userModel: User = User()
    private lateinit var userPreference: UserPreference

    private lateinit var connectivityManager: ConnectivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference(this)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (userPreference.getAuthToken() != null) {
            // Auth token exists, navigate to MainActivity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        monitorNetwork()

        binding.apply {
            btnForgotPassword.setOnClickListener {
                val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                startActivity(intent)
            }
            btnLogin.setOnClickListener {
                // Check network before login
                if (!isNetworkAvailable()) {
                    showNoInternetDialog()
                } else {
                    login()
                }
            }
        }
    }

    private fun login() {
        val etLoginNrp = binding.etLoginNrp.text
        val etLoginPassword = binding.etLoginPassword.text

        if (etLoginNrp!!.isEmpty() || etLoginPassword!!.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        } else if (etLoginPassword.length < 8) {
            Toast.makeText(this, "Please check your Password", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.login(
                etLoginNrp.toString(),
                etLoginPassword.toString()
            ).observe(this) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE

                        val response = result.data

                        userModel.token = response.token
                        userPreference.saveAuthToken(userModel.token!!)

                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                        saveAuthToken(userModel.token!!)
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE

                        if (result.error.message == "Invalid Email") {
                            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()
                        } else if (result.error.message == "Invalid Password") {
                            Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    // Function to check network availability
    private fun isNetworkAvailable(): Boolean {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    // Function to show a dialog when no internet
    private fun showNoInternetDialog() {
        AlertDialog.Builder(this)
            .setTitle("No Internet")
            .setMessage("Your internet connection is lost. Please check your connection.")
            .setPositiveButton("Retry") { _, _ ->
                if (isNetworkAvailable()) {
                    Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
                } else {
                    showNoInternetDialog()
                }
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .setCancelable(false)
            .show()
    }

    // Function to monitor network changes
    private fun monitorNetwork() {
        connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: android.net.Network) {
                runOnUiThread {
                    Toast.makeText(this@LoginActivity, "Internet Connected", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onLost(network: android.net.Network) {
                runOnUiThread {
                    showNoInternetDialog()
                }
            }
        })
    }

    // Save Auth Token
    private fun saveAuthToken(token: String) {
        val sharedPreferences = getSharedPreferences("USER_PREF", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("authToken", token)
        editor.apply()
    }
}
