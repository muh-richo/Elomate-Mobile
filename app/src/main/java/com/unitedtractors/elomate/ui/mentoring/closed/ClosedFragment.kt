package com.unitedtractors.elomate.ui.mentoring.closed

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.unitedtractors.elomate.adapter.MentoringAdapter
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.databinding.FragmentClosedBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.mentoring.MentoringViewModel
import com.unitedtractors.elomate.ui.mentoring.detail.DetailMentoringActivity

class ClosedFragment : Fragment() {

    private lateinit var binding: FragmentClosedBinding

    private val viewModel by viewModels<MentoringViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClosedBinding.inflate(layoutInflater)

        userPreference = UserPreference(requireContext())
        userModel = userPreference.getUser()

        setupRecyclerView("Bearer ${userModel.id}")

        return binding.root
    }

    private fun setupRecyclerView(token: String) {
        binding.rvClosedMentoring.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getApproveMentoring(token).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {

                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val upcomingList = result.data

                    val adapter = MentoringAdapter(upcomingList) { mentoringId ->
                        val intent = Intent(requireContext(), DetailMentoringActivity::class.java)
                        intent.putExtra("MENTORING_ID", mentoringId) // Kirim assignmentId
                        startActivity(intent)
                    }
                    binding.rvClosedMentoring.adapter = adapter
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}