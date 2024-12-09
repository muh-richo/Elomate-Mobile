package com.unitedtractors.elomate.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.adapter.ToDoAdapter
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.response.AssignmentResponse
import com.unitedtractors.elomate.databinding.ActivityToDoListBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.assigment.detail.DetailAssignmentActivity
import com.unitedtractors.elomate.ui.todo.ToDoViewModel

class ToDoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityToDoListBinding

    private val viewModel by viewModels<ToDoViewModel>() {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    private lateinit var allAssignmentToDo: List<AssignmentResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityToDoListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        userPreference = UserPreference(this)
        userModel = userPreference.getUser()

        setupRecyclerView()

        binding.icBack.setOnClickListener{
            finish()
        }

        binding.apply {
            icBack.setOnClickListener {
                finish()
            }
            btnAll.setOnClickListener {
                setActiveButton(binding.btnAll)
                filterAssignment("All")
            }
            btnPreTest.setOnClickListener {
                setActiveButton(binding.btnPreTest)
                filterAssignment("pre_activity")
            }
            btnPostTest.setOnClickListener {
                setActiveButton(binding.btnPostTest)
                filterAssignment("post_test")
            }
            btnTugas.setOnClickListener {
                setActiveButton(binding.btnTugas)
                filterAssignment("tugas")
            }
            btn360.setOnClickListener {
                setActiveButton(binding.btn360)
                filterAssignment("assessment")
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setupRecyclerView() {
        binding.rvTodoList.layoutManager = LinearLayoutManager(this)

        viewModel.getToDoList("Bearer ${userModel.id}").observe(this) { result ->
            when (result) {
                is Result.Loading -> {

                }
                is Result.Success -> {
                    allAssignmentToDo = result.data

                    val adapter = ToDoAdapter(allAssignmentToDo) { assignmentId ->
                        val intent = Intent(this, DetailAssignmentActivity::class.java)
                        intent.putExtra("ASSIGNMENT_ID", assignmentId)
                        startActivity(intent)
                    }
                    binding.rvTodoList.adapter = adapter

                    filterAssignment("All")
                }
                is Result.Error -> {
                    Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun setActiveButton(activeButton: LinearLayout) {
        resetButtonStyle(binding.btnAll)
        resetButtonStyle(binding.btnPreTest)
        resetButtonStyle(binding.btnPostTest)
        resetButtonStyle(binding.btnTugas)
        resetButtonStyle(binding.btn360)

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

    @SuppressLint("SetTextI18n")
    private fun filterAssignment(category: String) {
        val filteredList = if (category == "All") {
            allAssignmentToDo
        } else {
            allAssignmentToDo.filter { it.category == category }
        }

        val incompleteTask = filteredList.size
        binding.tvTotalTodoList.text = "Incomplete Task : $incompleteTask"

        (binding.rvTodoList.adapter as ToDoAdapter).updateData(filteredList)
    }

}
