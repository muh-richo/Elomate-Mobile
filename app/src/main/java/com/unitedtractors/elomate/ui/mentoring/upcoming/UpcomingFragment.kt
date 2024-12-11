package com.unitedtractors.elomate.ui.mentoring.upcoming

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
import com.unitedtractors.elomate.databinding.FragmentUpcomingBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.response.MentoringResponse
import com.unitedtractors.elomate.ui.mentoring.MentoringViewModel
import com.unitedtractors.elomate.ui.mentoring.detail.DetailMentoringActivity

class UpcomingFragment : Fragment() {

    private lateinit var binding: FragmentUpcomingBinding

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
        binding = FragmentUpcomingBinding.inflate(inflater, container, false)

        userPreference = UserPreference(requireContext())
        userModel = userPreference.getUser()

        setupRecyclerView()
        fetchUpcomingMentoring()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.rvUpcomingMentoring.layoutManager = LinearLayoutManager(requireContext())

        adapter = MentoringAdapter(
            mentoringList = mentoringList,
            onUpcomingClick = { mentoringId ->
                val intent = Intent(requireContext(), DetailMentoringActivity::class.java)
                intent.putExtra("MENTORING_ID", mentoringId)
                startActivity(intent)
            },
            onDeleteClick = { mentoringId ->
                deleteMentoring(mentoringId)
            }
        )

        binding.rvUpcomingMentoring.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchUpcomingMentoring() {
        viewModel.getUpcomingMentoring("Bearer ${userModel.id}").observe(viewLifecycleOwner) { result ->
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

    private fun deleteMentoring(mentoringId: Int) {
        viewModel.deleteMentoring("Bearer ${userModel.id}", mentoringId).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {  }
                is Result.Success -> {
                    Toast.makeText(requireContext(), "Mentoring berhasil dihapus", Toast.LENGTH_SHORT).show()
                    val position = mentoringList.indexOfFirst { it.mentoringId == mentoringId }
                    if (position != -1) {
                        mentoringList.removeAt(position)
                        adapter.notifyItemRemoved(position)
                    }
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), "Gagal menghapus mentoring: ${result.error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}