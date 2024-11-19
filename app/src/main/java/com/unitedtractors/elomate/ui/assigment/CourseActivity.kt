package com.unitedtractors.elomate.ui.assigment

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.databinding.ActivityCourseBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.assigment.postactivity.PostActivityFragment
import com.unitedtractors.elomate.ui.assigment.preactivity.activity.PreActivityFragment
import com.unitedtractors.elomate.ui.assigment.preactivity.PreFragment
import com.unitedtractors.elomate.ui.assignment.CourseViewModel

class   CourseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseBinding

    private val viewModel by viewModels<CourseViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        // Inisialisasi ViewPager dan TabLayout
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        // Set adapter untuk ViewPager
        viewPager.adapter = CoursePagerAdapter(this)

        // Hubungkan TabLayout dengan ViewPager dan terapkan custom tab view
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.customView = createTabView(position)
        }.attach()

        userPreference = UserPreference(this)
        userModel = userPreference.getUser()

        val courseId = intent.getIntExtra("COURSE_ID", -1)
        if (courseId != -1) {
            loadCourseDetails("Bearer ${userModel.id}", courseId)
        }

        binding.icBack.setOnClickListener {
            finish()
        }
    }

    // Custom tab view untuk tampilan tab
    private fun createTabView(position: Int): View {
        val tabTitles = listOf("Pre-Activity", "Post-Activity")
        val textView = TextView(this).apply {
            text = tabTitles[position]
            textSize = 14f
            gravity = Gravity.CENTER
        }
        return textView
    }

    private inner class CoursePagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int = 2 // Jumlah tab

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> PreFragment().apply {
                    arguments = Bundle().apply {
                        putInt("COURSE_ID", intent.getIntExtra("COURSE_ID", 0)) // Kirim courseId ke PreFragment
                    }
                }
                1 -> PostActivityFragment().apply {
                    arguments = Bundle().apply {
                        putInt("COURSE_ID", intent.getIntExtra("COURSE_ID", 0)) // Kirim courseId ke PostFragment
                    }
                }
                else -> throw IllegalStateException("Unexpected position $position")
            }
        }
    }

    private fun loadCourseDetails(token: String, courseId: Int) {
        viewModel.getCourseById(token, courseId).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    // Tampilkan indikator loading jika diperlukan
                }
                is Result.Success -> {
                    val courses = result.data // Ini adalah List<CourseResponse>
                    val courseDetails = courses.find { it.courseId == courseId }

                    if (courseDetails != null) {
                        // Tampilkan data detail di UI
                        binding.tvCourse.text = courseDetails.namaCourse
                        binding.tvBatch.text = courseDetails.batchName

                        val intent = Intent(this, PreActivityFragment::class.java)
                        intent.putExtra("COURSE_ID", courseId) // Mengirimkan courseId melalui Intent

                    } else {
                        // Tangani jika course tidak ditemukan
                        Toast.makeText(this, "Course not found", Toast.LENGTH_SHORT).show()
                    }
                }
                is Result.Error -> {
                    Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
