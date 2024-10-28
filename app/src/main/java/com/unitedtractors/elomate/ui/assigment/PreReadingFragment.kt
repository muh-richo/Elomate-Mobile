package com.unitedtractors.elomate.ui.assigment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.unitedtractors.elomate.databinding.FragmentPreActivityBinding
import com.unitedtractors.elomate.databinding.FragmentPreReadingBinding

class PreReadingFragment : Fragment() {

    private var _binding: FragmentPreReadingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreReadingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set click listener for announcement
        binding.materiCard.setOnClickListener {
            val intent = Intent(activity, DetailPreReadingActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
