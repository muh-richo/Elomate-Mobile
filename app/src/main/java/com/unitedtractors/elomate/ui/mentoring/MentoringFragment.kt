package com.unitedtractors.elomate.ui.mentoring

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.databinding.FragmentMentoringBinding
import com.unitedtractors.elomate.ui.mentoring.closed.ClosedFragment
import com.unitedtractors.elomate.ui.mentoring.create.CreateFragment
import com.unitedtractors.elomate.ui.mentoring.feedback.FeedbackFragment
import com.unitedtractors.elomate.ui.mentoring.upcoming.UpcomingFragment

class MentoringFragment : Fragment() {

    lateinit var binding: FragmentMentoringBinding

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMentoringBinding.inflate(layoutInflater)

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

//        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                val currentFragment = childFragmentManager.findFragmentByTag("f$position")
//                if (currentFragment != null) {
//                    adjustViewPagerHeight(viewPager, currentFragment)
//                }
//            }
//        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        userPreference = UserPreference(requireContext())
        userModel = userPreference.getUser()

    }

    private fun createTabView(position: Int): View {
        val tabTitles = listOf("Create", "Upcoming", "Feedback", "Closed")
        val textView = TextView(context).apply {
            text = tabTitles[position]
            textSize = 14f
            gravity = android.view.Gravity.CENTER
        }
        return textView
    }

    private fun adjustViewPagerHeight(viewPager: ViewPager2, fragment: Fragment) {
        fragment.view?.post {
            val measuredHeight = fragment.view?.measuredHeight ?: 0
            val layoutParams = viewPager.layoutParams
            layoutParams.height = measuredHeight
            viewPager.layoutParams = layoutParams
        }
    }

    private inner class MentoringPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 4

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> CreateFragment()
                1 -> UpcomingFragment()
                2 -> FeedbackFragment()
                3 -> ClosedFragment()
                else -> throw IllegalStateException("Unexpected position $position")
            }
        }
    }
}
