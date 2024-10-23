package com.unitedtractors.elomate.ui.mentoring

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.unitedtractors.elomate.databinding.FragmentCreateBinding

class CreateFragment : Fragment() {

    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)

        setupSpinner()

        return binding.root
    }

    private fun setupSpinner() {
        // Define the array of items with the first item as a hint
        val metode = arrayOf("Pilih metode", "Online", "Offline")
        val adapterMetode = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, metode)

        val tipe = arrayOf("Pilih tipe", "Coaching", "Mentoring")
        val adapterTipe = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tipe)

        val kompetensi = arrayOf("Pilih kompetensi", "Kompetensi 1", "Kompetensi 2")
        val adapterKompetensi = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, kompetensi)

        // Set the dropdown view resource
        adapterMetode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterTipe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterKompetensi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set adapters for the Spinners
        binding.spinnerMetode.adapter = adapterMetode
        binding.spinnerTipe.adapter = adapterTipe
        binding.spinnerKompetensi.adapter = adapterKompetensi

        // Optional: Set a default hint text color or behavior (if necessary)
        binding.spinnerMetode.setSelection(0)
        binding.spinnerTipe.setSelection(0)
        binding.spinnerKompetensi.setSelection(0)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
