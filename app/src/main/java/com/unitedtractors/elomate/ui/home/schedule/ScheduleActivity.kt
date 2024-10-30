package com.unitedtractors.elomate.ui.home.schedule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.unitedtractors.elomate.databinding.ActivityScheduleBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScheduleBinding
    private val taskDeadlines = listOf(
        Task("2024-10-30", "Tugas 1"),
        Task("2024-11-02", "Tugas 2"),
        Task("2024-11-05", "Tugas 3"),
        Task("2024-11-10", "Tugas 4"),
        Task("2024-11-10", "Tugas 5")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = Calendar.getInstance()
            date.set(year, month, dayOfMonth)

            displayTasksForDate(date.timeInMillis)
        }

        // Set RecyclerView properties
        binding.rvTasks.layoutManager = LinearLayoutManager(this)


        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun displayTasksForDate(selectedDate: Long) {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val selectedDateStr = formatter.format(Date(selectedDate))

        // Filter tasks based on selected date
        val tasksForDate = taskDeadlines.filter { it.deadline == selectedDateStr }

        // Update RecyclerView adapter
        binding.rvTasks.adapter = TaskAdapter(tasksForDate)
    }
}
