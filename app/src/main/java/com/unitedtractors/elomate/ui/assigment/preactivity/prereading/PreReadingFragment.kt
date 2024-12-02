package com.unitedtractors.elomate.ui.assigment.preactivity.prereading

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.unitedtractors.elomate.adapter.PreReadingAdapter
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.databinding.FragmentPreReadingBinding
import com.unitedtractors.elomate.ui.ViewModelFactory

class PreReadingFragment : Fragment() {

    private lateinit var binding: FragmentPreReadingBinding

    private val viewModel by viewModels<PreReadingViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreReadingBinding.inflate(layoutInflater)

        userPreference = UserPreference(requireContext())
        userModel = userPreference.getUser()

        val courseId = arguments?.getInt("COURSE_ID", 0) ?: 0 // Ambil nilai courseId dari arguments

        if (courseId != 0) {
            setupRecyclerView("Bearer ${userModel.id}", courseId)
        } else {
            Toast.makeText(requireContext(), "Course ID not found", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun setupRecyclerView(token: String, courseId: Int) {
        binding.rvPreReading.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getPreReadingByCourseId(token, courseId).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val preReadingList = result.data

                    val adapter = PreReadingAdapter(preReadingList) { preReadingId ->
                        val intent = Intent(requireContext(), DetailPreReadingActivity::class.java)
                        intent.putExtra("PRE_READING_ID", preReadingId) // Kirim assignmentId
                        startActivity(intent)
                    }
                    binding.rvPreReading.adapter = adapter
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
