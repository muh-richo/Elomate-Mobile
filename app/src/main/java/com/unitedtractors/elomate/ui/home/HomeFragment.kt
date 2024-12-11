package com.unitedtractors.elomate.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.view.ViewContainer
import com.kizitonwose.calendar.view.WeekDayBinder
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.adapter.CourseAdapter
import com.unitedtractors.elomate.adapter.CourseProgressAdapter
import com.unitedtractors.elomate.adapter.ScheduleAdapter
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
import com.unitedtractors.elomate.ui.assigment.course.CourseActivity
import com.unitedtractors.elomate.ui.assigment.detail.DetailAssignmentActivity
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    private var selectedDate: LocalDate? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreference = UserPreference(requireContext())
        userModel = userPreference.getUser()

        getCurrentUserApi()
        setupRecyclerViewToDo()
        getCourseProgress()

        setupWeekCalendarView()
        setupClickListeners()
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

    private fun getCourseProgress() {
        viewModel.getCourseProgress("Bearer ${userModel.id}").observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressHorizontal.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressHorizontal.visibility = View.GONE

                    val courseProgress = result.data
                    val adapter = CourseProgressAdapter(courseProgress) { courseId ->
                        val intent = Intent(requireContext(), CourseActivity::class.java)
                        intent.putExtra("COURSE_ID", courseId)
                        startActivity(intent)
                    }
                    binding.rvCourseProgress.layoutManager = LinearLayoutManager(requireContext())
                    binding.rvCourseProgress.adapter = adapter
                }
                is Result.Error -> {
                    binding.progressHorizontal.visibility = View.GONE
                    Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupWeekCalendarView() {
        val weekCalendarView = binding.weekCalendarView

        val currentDate = LocalDate.now()
        val currentMonth = YearMonth.now()
        val startDate = currentMonth.minusMonths(100).atStartOfMonth()
        val endDate = currentMonth.plusMonths(100).atEndOfMonth()
        val firstDayOfWeek = firstDayOfWeekFromLocale()

        var selectedDate: LocalDate? = null

        weekCalendarView.setup(startDate, endDate, firstDayOfWeek)
        weekCalendarView.scrollToWeek(currentDate)

        weekCalendarView.dayBinder = object : WeekDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)

            @SuppressLint("ResourceAsColor")
            override fun bind(container: DayViewContainer, data: WeekDay) {
                container.textView.text = data.date.dayOfMonth.toString()

                // Atur warna background
                when {
                    data.date == selectedDate -> {
                        container.view.setBackgroundResource(R.drawable.bg_date2)
                    }
                    else -> {
                        container.view.setBackgroundColor(Color.TRANSPARENT)
                    }
                }

                // Atur warna teks
                container.textView.setTextColor(
                    when {
                        data.date == LocalDate.now() -> Color.BLUE
                        data.date.dayOfWeek == DayOfWeek.SUNDAY -> Color.RED
                        else -> Color.BLACK
                    }
                )

                // Tambahkan klik listener
                container.view.setOnClickListener {
                    // Update tanggal yang dipilih
                    val previousSelectedDate = selectedDate
                    selectedDate = data.date

                    // Refresh tampilan kalender agar tanggal sebelumnya diperbarui
                    if (previousSelectedDate != null) {
                        weekCalendarView.notifyDateChanged(previousSelectedDate)
                    }
                    weekCalendarView.notifyDateChanged(selectedDate!!)

                    // Panggil fungsi untuk memuat jadwal
                    getToDoListScheduleForDate(data.date)
                }
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun getToDoListScheduleForDate(date: LocalDate) {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val deadline = date.format(formatter)
        viewModel.getToDoListSchedule("Bearer ${userModel.id}", deadline).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressHorizontal.visibility = View.VISIBLE
                    binding.tvNoTasks.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.progressHorizontal.visibility = View.GONE
                    binding.rvScheduleList.visibility = View.VISIBLE
                    binding.tvNoTasks.visibility = View.GONE

                    val tasks = result.data

                    binding.tvScheduleCount.text = tasks.size.toString()

                    val adapter = ScheduleAdapter(tasks) { assignmentId ->
                        val intent = Intent(requireContext(), DetailAssignmentActivity::class.java)
                        intent.putExtra("ASSIGNMENT_ID", assignmentId)
                        startActivity(intent)
                    }
                    binding.rvScheduleList.layoutManager = LinearLayoutManager(requireContext())
                    binding.rvScheduleList.adapter = adapter
                }
                is Result.Error -> {
                    binding.progressHorizontal.visibility = View.GONE
                    binding.rvScheduleList.visibility = View.GONE
                    binding.tvNoTasks.visibility = View.VISIBLE

                    binding.tvScheduleCount.text = "0"
                    binding.tvNoTasks.text = "Tidak ada tugas untuk tanggal ini."
                }
            }
        }
    }

    private fun firstDayOfWeekFromLocale(): DayOfWeek {
        return DayOfWeek.of(java.util.Calendar.getInstance().firstDayOfWeek)
    }

    inner class DayViewContainer(view: View) : ViewContainer(view) {
        val textView: TextView = view.findViewById(R.id.calendarDayText)
    }
}
