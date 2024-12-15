package com.unitedtractors.elomate.ui.mentoring.feedback

import android.annotation.SuppressLint
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
import com.unitedtractors.elomate.data.network.response.MentoringResponse
import com.unitedtractors.elomate.databinding.FragmentFeedbackBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.mentoring.MentoringViewModel
import com.unitedtractors.elomate.ui.mentoring.detail.DetailMentoringActivity

class FeedbackFragment : Fragment() {

    private lateinit var binding: FragmentFeedbackBinding

    private val viewModel by viewModels<MentoringViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    private lateinit var adapter: MentoringAdapter
    private val mentoringList = mutableListOf<MentoringResponse>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedbackBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        userPreference = UserPreference(requireContext())
        userModel = userPreference.getUser()

        setupRecyclerView()
        fetchFeedbackMentoring()
    }

    private fun setupRecyclerView() {
        binding.rvFeedbackMentoring.layoutManager = LinearLayoutManager(requireContext())

        adapter = MentoringAdapter(
            mentoringList = mentoringList,
            onUpcomingClick = { mentoringId ->
                val intent = Intent(requireContext(), DetailMentoringActivity::class.java)
                intent.putExtra("MENTORING_ID", mentoringId)
                startActivity(intent)
            },
            onDeleteClick = { mentoringId ->
                //
            }
        )

        binding.rvFeedbackMentoring.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchFeedbackMentoring() {
        viewModel.getFeedbackMentoring("Bearer ${userModel.token}").observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    mentoringList.clear()
                    mentoringList.addAll(result.data)
                    adapter.notifyDataSetChanged()
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}