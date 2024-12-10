package com.unitedtractors.elomate.ui.schedule

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.databinding.ActivityScheduleBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.home.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.widget.Toast
import com.unitedtractors.elomate.adapter.ScheduleAdapter
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.ui.assigment.detail.DetailAssignmentActivity

class ScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScheduleBinding

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        userPreference = UserPreference(this)
        userModel = userPreference.getUser()

        // Format untuk tanggal
        val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = Calendar.getInstance()
            date.set(year, month, dayOfMonth)

            // Format tanggal ke "dd-MM-yyyy"
            val formattedDate = dateFormatter.format(date.time)

            // Panggil API dengan token dan tanggal
            getToDoListSchedule(formattedDate)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun getToDoListSchedule(deadline: String) {
        viewModel.getToDoListSchedule("Bearer ${userModel.id}", deadline).observe(this) { result ->
            when (result) {
                is Result.Loading -> {  }
                is Result.Success -> {
                    val response = result.data

                    val adapter = ScheduleAdapter(response) { assignmentId ->
                        val intent = Intent(this, DetailAssignmentActivity::class.java)
                        intent.putExtra("ASSIGNMENT_ID", assignmentId)
                        startActivity(intent)
                    }
                    binding.rvTasks.layoutManager = LinearLayoutManager(this)
                    binding.rvTasks.adapter = adapter
                }
                is Result.Error -> {
                    Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}
