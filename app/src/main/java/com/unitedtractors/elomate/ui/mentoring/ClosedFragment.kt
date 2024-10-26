package com.unitedtractors.elomate.ui.mentoring

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.unitedtractors.elomate.databinding.FragmentClosedBinding

class ClosedFragment : Fragment() {

    private var _binding : FragmentClosedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClosedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set click listener
        binding.buttonDetail.setOnClickListener {
            val intent = Intent(activity, FeedbackFormActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}