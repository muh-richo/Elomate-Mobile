package com.unitedtractors.elomate.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.arjungupta08.horizontal_calendar_date.HorizontalCalendarAdapter
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.view.WeekDayBinder
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.databinding.FragmentHomeBinding
import com.unitedtractors.elomate.ui.home.schedule.DayViewContainer
import com.unitedtractors.elomate.ui.home.schedule.ScheduleActivity
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

class HomeFragment : Fragment(), HorizontalCalendarAdapter.OnItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        weekCalendarView()

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

        return root
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

    override fun onItemClick(ddMmYy: String, dd: String, day: String) {
        // Set selected date text
        binding.selectedDate.text = "Selected Date: $ddMmYy"
    }
}
