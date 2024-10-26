package com.unitedtractors.elomate.ui.assigment

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.ui.assigment.PostFragment
import com.unitedtractors.elomate.ui.assigment.PreFragment

class CourseActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)

        // Inisialisasi ViewPager dan TabLayout
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        // Set adapter untuk ViewPager
        viewPager.adapter = CoursePagerAdapter(this)

        // Hubungkan TabLayout dengan ViewPager dan terapkan custom tab view
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.customView = createTabView(position)
        }.attach()

        // Set click listener for the back button
        findViewById<View>(R.id.ic_back).setOnClickListener {
            finish() // Kembali ke halaman sebelumnya
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
                0 -> PreFragment() // Fragment pertama
                1 -> PostFragment() // Fragment kedua
                else -> throw IllegalStateException("Unexpected position $position")
            }
        }
    }
}
