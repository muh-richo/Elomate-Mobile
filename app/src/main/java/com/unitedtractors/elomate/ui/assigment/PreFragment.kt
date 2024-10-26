package com.unitedtractors.elomate.ui.assigment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.unitedtractors.elomate.R

class PreFragment : Fragment() {

    private lateinit var btnActivity: LinearLayout
    private lateinit var btnPreReading: LinearLayout
    private lateinit var txtActivity: TextView
    private lateinit var txtPreReading: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pre, container, false)

        // Inisialisasi tombol dan TextView
        btnActivity = view.findViewById(R.id.btn_activity)
        btnPreReading = view.findViewById(R.id.btn_preReading)
        txtActivity = view.findViewById(R.id.title_activity)
        txtPreReading = view.findViewById(R.id.title_preReading)

        // Set klik listener untuk setiap tombol
        btnActivity.setOnClickListener {
            setActiveButton(btnActivity, txtActivity, true)
            setActiveButton(btnPreReading, txtPreReading, false)
            replaceFragment(PreActivityFragment())
        }

        btnPreReading.setOnClickListener {
            setActiveButton(btnPreReading, txtPreReading, true)
            setActiveButton(btnActivity, txtActivity, false)
            replaceFragment(PreReadingFragment())
        }

        // Set kondisi awal, tombol Activity aktif
        setActiveButton(btnActivity, txtActivity, true)
        setActiveButton(btnPreReading, txtPreReading, false)
        replaceFragment(PreActivityFragment())

        return view
    }

    // Fungsi untuk mengatur gaya tombol berdasarkan status aktif/tidak aktif
    private fun setActiveButton(button: LinearLayout, textView: TextView, isActive: Boolean) {
        if (isActive) {
            button.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_title)
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.shades_100))
        } else {
            button.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_title_gray)
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.neutral_400))
        }
    }

    // Fungsi untuk mengganti fragment dalam kontainer
    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_pre, fragment)
            .commit()
    }
}
