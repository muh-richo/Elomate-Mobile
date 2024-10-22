package com.unitedtractors.elomate.ui.assigment

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.unitedtractors.elomate.databinding.FragmentAssigmentBinding

class
AssigmentFragment : Fragment() {

    private var _binding: FragmentAssigmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(AssigmentViewModel::class.java)

        _binding = FragmentAssigmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        // Find the FrameLayout that includes both ProgressBar and TextView
//        val progressBar = binding.circleProgressbar
//        val progressText = binding.progressText
//
//        // Set the progress value
//        val progressValue = 50 // Example progress value
//        progressBar.progress = progressValue
//        progressText.text = "$progressValue%"

        // Initialize the Spinner
        setupSpinner()

        return root
    }

    private fun setupSpinner() {
        // Define the array of items
        val phases = arrayOf("Phase 1", "Phase 2", "Phase 3", "Phase 4", "Phase 10")
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, phases)

        val topic = arrayOf("General Development", "Orientasi Divisi", "BGMS", "NEOP")
        val adapter2 = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, topic)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner: Spinner = binding.spinnerDropdown
        val spinner2: Spinner = binding.spinnerDropdown2

        spinner.adapter = adapter
        spinner2.adapter = adapter2
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}