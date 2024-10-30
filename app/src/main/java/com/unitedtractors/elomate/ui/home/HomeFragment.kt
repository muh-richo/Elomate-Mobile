package com.unitedtractors.elomate.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.unitedtractors.elomate.databinding.FragmentHomeBinding
import com.unitedtractors.elomate.ui.home.schedule.ScheduleActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set click listener for announcement
        binding.announcement.setOnClickListener {
            val intent = Intent(activity, AnnouncementActivity::class.java)
            startActivity(intent)
        }

        // Set click listener for ic_notif (assuming it exists in the layout)
        binding.icNotif.setOnClickListener {
            val intent = Intent(activity, AnnouncementActivity::class.java)
            startActivity(intent)
        }

        binding.allTodo.setOnClickListener{
            val intent = Intent(activity, ToDoListActivity::class.java)
            startActivity(intent)
        }

        binding.iconAlltodo.setOnClickListener{
            val intent = Intent(activity, ToDoListActivity::class.java)
            startActivity(intent)
        }

        binding.allSchedule.setOnClickListener{
            val intent = Intent(activity, ScheduleActivity::class.java)
            startActivity(intent)
        }

        binding.iconAllSchedule.setOnClickListener{
            val intent = Intent(activity, ScheduleActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
