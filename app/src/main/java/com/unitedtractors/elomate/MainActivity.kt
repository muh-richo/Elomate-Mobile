package com.unitedtractors.elomate

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.databinding.ActivityMainBinding
import com.unitedtractors.elomate.ui.auth.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var userPreference: UserPreference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference(this)

        // Redirect to login if no auth token is found
        if (userPreference.getAuthToken() == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_assigment, R.id.navigation_mentoring, R.id.navigation_report, R.id.navigation_profil
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}