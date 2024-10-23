package com.unitedtractors.elomate.ui.mentoring

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.unitedtractors.elomate.databinding.FragmentMentoringBinding

class MentoringFragment : Fragment() {

    private var _binding: FragmentMentoringBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMentoringBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager

        // Setup the ViewPager adapter for managing fragments for each tab
        val adapter = MentoringPagerAdapter(requireActivity())
        viewPager.adapter = adapter

        // Set the default tab to "Create" (position 0)
        viewPager.currentItem = 0

        // Connect TabLayout and ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.customView = createTabView(position)
        }.attach()

        return root
    }

    // Function to create a custom tab view
    private fun createTabView(position: Int): View {
        val tabTitles = listOf("Create", "Upcoming", "Feedback", "Closed")
        val textView = TextView(context).apply {
            text = tabTitles[position]
            textSize = 14f
            gravity = android.view.Gravity.CENTER
        }
        return textView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Adapter for managing fragments
    private inner class MentoringPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 4

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> CreateFragment() // Use a dedicated CreateFragment for "Create"
                1 -> UpcomingFragment() // Fragment for "Upcoming" tab
                2 -> FeedbackFragment() // Fragment for "Feedback" tab
                3 -> ClosedFragment() // Fragment for "Closed" tab
                else -> throw IllegalStateException("Unexpected position $position")
            }
        }
    }
}
