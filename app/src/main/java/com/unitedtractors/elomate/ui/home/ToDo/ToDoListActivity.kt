package com.unitedtractors.elomate.ui.home

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.ui.home.ToDo.Task
import com.unitedtractors.elomate.ui.home.ToDo.ToDoAdapter

class ToDoListActivity : AppCompatActivity() {

    private lateinit var taskAdapter: ToDoAdapter
    private val allTasks = listOf(
        Task("Course 1", "Pre-Test 1", "2024-10-30", "Pending"),
        Task("Course 2", "Post-Test 1", "2024-11-02", "Complete"),
        Task("Course 3", "Tugas 1", "2024-11-05", "Pending"),
        Task("Course 4", "Self Peer Assessment 1", "2024-11-10", "Complete"),
        Task("Course 1", "Pre-Test 2", "2024-11-10", "Pending")
    )

    // Tombol filter
    private lateinit var btnAll: LinearLayout
    private lateinit var btnPreTest: LinearLayout
    private lateinit var btnPostTest: LinearLayout
    private lateinit var btnTugas: LinearLayout
    private lateinit var btn360: LinearLayout

    // TextView untuk menampilkan total tugas yang belum selesai
    private lateinit var totalTaskTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)

        // Set up RecyclerView
        val rvTasks = findViewById<RecyclerView>(R.id.rv_tasks)
        taskAdapter = ToDoAdapter(allTasks)
        rvTasks.layoutManager = LinearLayoutManager(this)
        rvTasks.adapter = taskAdapter

        // Back button functionality
        findViewById<View>(R.id.ic_back).setOnClickListener {
            finish()
        }

        // Inisialisasi tombol
        btnAll = findViewById(R.id.btnAll)
        btnPreTest = findViewById(R.id.btnPreTest)
        btnPostTest = findViewById(R.id.btnPostTest)
        btnTugas = findViewById(R.id.btnTugas)
        btn360 = findViewById(R.id.btn360)

        // Inisialisasi TextView untuk total tugas yang belum selesai
        totalTaskTextView = findViewById(R.id.TotalTask)

        // Set click listeners for filter buttons
        btnAll.setOnClickListener { setActiveButton(btnAll); filterTasks("All") }
        btnPreTest.setOnClickListener { setActiveButton(btnPreTest); filterTasks("Pre-Test") }
        btnPostTest.setOnClickListener { setActiveButton(btnPostTest); filterTasks("Post-Test") }
        btnTugas.setOnClickListener { setActiveButton(btnTugas); filterTasks("Tugas") }
        btn360.setOnClickListener { setActiveButton(btn360); filterTasks("Self Peer Assessment") }

        // Update total incomplete tasks on first load
        updateIncompleteTaskCount(allTasks)
    }

    private fun setActiveButton(activeButton: LinearLayout) {
        // Reset style semua tombol
        resetButtonStyle(btnAll)
        resetButtonStyle(btnPreTest)
        resetButtonStyle(btnPostTest)
        resetButtonStyle(btnTugas)
        resetButtonStyle(btn360)

        // Set style tombol aktif
        activeButton.setBackgroundResource(R.drawable.bg_title)
        val textView = activeButton.getChildAt(0) as TextView
        textView.setTextColor(getColor(R.color.shades_100))
    }

    private fun resetButtonStyle(button: LinearLayout) {
        button.setBackgroundResource(R.drawable.bg_title_gray)
        val textView = button.getChildAt(0) as TextView
        textView.setTextColor(getColor(R.color.neutral_400))
    }

    private fun filterTasks(category: String) {
        val filteredTasks = when (category) {
            "All" -> allTasks
            "Pre-Test" -> allTasks.filter { it.name.contains("Pre-Test") }
            "Post-Test" -> allTasks.filter { it.name.contains("Post-Test") }
            "Tugas" -> allTasks.filter { it.name.contains("Tugas") }
            "Self Peer Assessment" -> allTasks.filter { it.name.contains("Self Peer Assessment") }
            else -> allTasks
        }
        taskAdapter.updateTasks(filteredTasks)
        updateIncompleteTaskCount(filteredTasks)
    }

    private fun updateIncompleteTaskCount(tasks: List<Task>) {
        // Menghitung jumlah tugas yang berstatus "Pending"
        val incompleteCount = tasks.count { it.status == "Pending" }
        totalTaskTextView.text = "Incomplete Task : $incompleteCount"
    }
}
