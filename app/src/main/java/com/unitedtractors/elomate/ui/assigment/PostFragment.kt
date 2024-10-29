package com.unitedtractors.elomate.ui.assigment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.unitedtractors.elomate.databinding.FragmentClosedBinding
import com.unitedtractors.elomate.databinding.FragmentPostBinding
import com.unitedtractors.elomate.ui.mentoring.FeedbackFormActivity

class PostFragment : Fragment() {

    private var _binding : FragmentPostBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set click listener
        binding.cardAssigment.setOnClickListener {
            val intent = Intent(activity, DetailAssigment2Activity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}