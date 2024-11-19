package com.unitedtractors.elomate.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.view.WeekDayBinder
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.adapter.ToDoAdapter
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.databinding.FragmentHomeBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.auth.login.LoginActivity
import com.unitedtractors.elomate.ui.announcement.AnnouncementActivity
import com.unitedtractors.elomate.ui.schedule.DayViewContainer
import com.unitedtractors.elomate.ui.schedule.ScheduleActivity
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.ui.assigment.detail.DetailAssignmentActivity
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        userPreference = UserPreference(requireContext())
        userModel = userPreference.getUser()

        getCurrentUserApi()
        setupRecyclerViewToDo()

        // Setup the calendar view
        weekCalendarView()
        setupClickListeners()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun setupClickListeners() {
        binding.announcement.setOnClickListener {
            val intent = Intent(activity, AnnouncementActivity::class.java)
            startActivity(intent)
        }

        binding.icNotif.setOnClickListener {
            val intent = Intent(activity, AnnouncementActivity::class.java)
            startActivity(intent)
        }

        binding.tvAllTodo.setOnClickListener {
            val intent = Intent(activity, ToDoActivity::class.java)
            startActivity(intent)
        }

        binding.ivAllTodo.setOnClickListener {
            val intent = Intent(activity, ToDoActivity::class.java)
            startActivity(intent)
        }

        binding.allSchedule.setOnClickListener {
            val intent = Intent(activity, ScheduleActivity::class.java)
            startActivity(intent)
        }

        binding.iconAllSchedule.setOnClickListener {
            val intent = Intent(activity, ScheduleActivity::class.java)
            startActivity(intent)
        }
    }
    private fun getCurrentUserApi() {
        viewModel.getCurrentUserApi("Bearer ${userModel.id}").observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressHorizontal.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressHorizontal.visibility = View.GONE

                        val response = result.data
                        binding.tvHiUser.text = getString(R.string.hi_user, response.namaLengkap)
                        binding.tvRole.text = response.roleName
                    }

                    is Result.Error -> {
                        binding.progressHorizontal.visibility = View.GONE

                        val errorMessage = result.error.message

                        if (errorMessage == "timeout") {
                            userModel.id = ""
                            userPreference.setUser(userModel)

                            Toast.makeText(requireContext(), "Sesi telah berakhir. Silakan login kembali.", Toast.LENGTH_SHORT).show()

                            val intent = Intent(requireContext(), LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerViewToDo() {
        binding.rvTodoList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getToDoList("Bearer ${userModel.id}").observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressHorizontal.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    binding.progressHorizontal.visibility = View.GONE

                    val todoList = result.data.take(3)

                    binding.tvAllTodo.visibility = if (result.data.size > 3) View.VISIBLE else View.GONE
                    binding.ivAllTodo.visibility = if (result.data.size > 3) View.VISIBLE else View.GONE

                    val adapter = ToDoAdapter(todoList) { assignmentId ->
                        val intent = Intent(requireContext(), DetailAssignmentActivity::class.java)
                        intent.putExtra("ASSIGNMENT_ID", assignmentId)
                        startActivity(intent)
                    }
                    binding.rvTodoList.adapter = adapter
                }

                is Result.Error -> {
                    binding.progressHorizontal.visibility = View.GONE
                    Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun weekCalendarView(){
        val weekCalendarView = binding.weekCalendarView


        val currentDate = LocalDate.now()
        val currentMonth = YearMonth.now()
        val startDate = currentMonth.minusMonths(100).atStartOfMonth() // Adjust as needed
        val endDate = currentMonth.plusMonths(100).atEndOfMonth() // Adjust as needed
        val firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library

        weekCalendarView.setup(startDate, endDate, firstDayOfWeek)
        weekCalendarView.scrollToWeek(currentDate)

        // Atur binder untuk setiap sel hari
        weekCalendarView.dayBinder = object : WeekDayBinder<DayViewContainer> {
            // Dipanggil hanya saat container baru dibutuhkan
            override fun create(view: View) = DayViewContainer(view)

            // Dipanggil setiap kali kita perlu menggunakan kembali container
            override fun bind(container: DayViewContainer, data: WeekDay) {
                // Set tanggal pada TextView
                container.textView.text = data.date.dayOfMonth.toString()

                // Set nama hari pada TextView
                val dayOfWeek = data.date.dayOfWeek

                // Mengatur warna latar belakang untuk hari ini
                if (data.date == LocalDate.now()) {
                    container.itemView.setBackgroundResource(R.drawable.bg_date)
                } else {
                    container.itemView.setBackgroundColor(Color.TRANSPARENT) // Reset latar belakang untuk hari lain
                    // Atur warna teks default
                    if (dayOfWeek == DayOfWeek.SUNDAY) {
                        container.textView.setTextColor(Color.RED)
                    } else {
                        container.textView.setTextColor(Color.BLACK)
                    }
                }

                container.itemView.setOnClickListener {
                    Toast.makeText(container.itemView.context, "Diklik pada: ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun firstDayOfWeekFromLocale(): DayOfWeek {
        // Menentukan hari pertama minggu dari locale saat ini
        return DayOfWeek.of(java.util.Calendar.getInstance().firstDayOfWeek)
    }

}
