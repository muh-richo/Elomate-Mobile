package com.unitedtractors.elomate.ui.home

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.view.WeekDayBinder
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.databinding.FragmentHomeBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.auth.login.LoginActivity
import com.unitedtractors.elomate.ui.home.announcement.AnnouncementActivity
import com.unitedtractors.elomate.ui.schedule.DayViewContainer
import com.unitedtractors.elomate.ui.schedule.ScheduleActivity
import com.unitedtractors.elomate.data.network.Result
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var userPreference: UserPreference

    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    // Initialize user model to avoid UninitializedPropertyAccessException
    private var userModel: User? = User()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize UserPreference
        userPreference = UserPreference(requireContext())

        // Call to fetch user data
        getCurrentUserApi()

        // Setup the calendar view
        weekCalendarView()

        // Set up click listeners
        setupClickListeners()

        return root
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

        binding.allTodo.setOnClickListener {
            val intent = Intent(activity, ToDoListActivity::class.java)
            startActivity(intent)
        }

        binding.iconAlltodo.setOnClickListener {
            val intent = Intent(activity, ToDoListActivity::class.java)
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

    private fun getAuthToken(): String? {
        val sharedPreferences = requireContext().getSharedPreferences("YourPreferenceName", Context.MODE_PRIVATE)
        return sharedPreferences.getString("authToken", null)
    }

    private fun getCurrentUserApi() {
        val authToken = userPreference.getAuthToken()

        if (!authToken.isNullOrEmpty()) {
            viewModel.getCurrentUserApi("Bearer $authToken").observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> { /* Show loading if necessary */ }
                    is Result.Success -> {
                        val response = result.data
                        binding.tvHiUser.text = getString(R.string.hi_user, response.namaLengkap ?: "User")
                    }
                    is Result.Error -> handleError(result.error.message)
                }
            }
        } else {
            Toast.makeText(requireContext(), "No token found. Please log in.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
    }

    private fun handleError(errorMessage: String?) {
        if (errorMessage == "Unauthorized") {
            Toast.makeText(requireContext(), "Session expired. Please log in again.", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(requireContext(), errorMessage ?: "An error occurred", Toast.LENGTH_SHORT).show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
