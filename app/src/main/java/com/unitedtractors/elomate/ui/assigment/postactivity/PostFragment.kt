package com.unitedtractors.elomate.ui.assigment.postactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.unitedtractors.elomate.databinding.FragmentPostBinding

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
//            val intent = Intent(activity, DetailAssigmentActivity::class.java)
//            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}