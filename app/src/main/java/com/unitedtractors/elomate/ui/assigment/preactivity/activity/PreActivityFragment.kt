package com.unitedtractors.elomate.ui.assigment.preactivity.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.unitedtractors.elomate.adapter.PreActivityAdapter
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.databinding.FragmentPreActivityBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.assigment.detail.DetailAssignmentActivity

class PreActivityFragment : Fragment() {

    private lateinit var binding: FragmentPreActivityBinding

    private val viewModel by viewModels<PreActivityViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreActivityBinding.inflate(layoutInflater)

        userPreference = UserPreference(requireContext())
        userModel = userPreference.getUser()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val courseId = arguments?.getInt("COURSE_ID", 0) ?: 0
        if (courseId != 0) {
            setupRecyclerView("Bearer ${userModel.token}", courseId)
        } else {
            Toast.makeText(requireContext(), "Course ID not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView(token: String, courseId: Int) {
        binding.rvPreActivity.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getPreActivityByCourseId(token, courseId).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val preActivityList = result.data

                    val adapter = PreActivityAdapter(preActivityList) { assignmentId ->
                        val intent = Intent(requireContext(), DetailAssignmentActivity::class.java)
                        intent.putExtra("ASSIGNMENT_ID", assignmentId) // Kirim assignmentId
                        startActivity(intent)
                    }
                    binding.rvPreActivity.adapter = adapter
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
