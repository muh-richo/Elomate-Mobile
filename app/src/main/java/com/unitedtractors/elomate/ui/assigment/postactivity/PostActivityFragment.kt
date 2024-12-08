package com.unitedtractors.elomate.ui.assigment.postactivity

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
import com.unitedtractors.elomate.adapter.PostActivityAdapter
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.databinding.FragmentPostBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.assigment.detail.DetailAssignmentActivity

class PostActivityFragment : Fragment() {

    private lateinit var binding: FragmentPostBinding

    private val viewModel by viewModels<PostActivityViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreference = UserPreference(requireContext())
        userModel = userPreference.getUser()

    }

    override fun onResume() {
        super.onResume()
        val courseId = arguments?.getInt("COURSE_ID", 0) ?: 0
        if (courseId != 0) {
            setupRecyclerView("Bearer ${userModel.id}", courseId)
        } else {
            Toast.makeText(requireContext(), "Course ID not found", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupRecyclerView(token: String, courseId: Int) {
        binding.rvPostActivity.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getPostActivityByCourseId(token, courseId).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBarLoading.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBarLoading.visibility = View.GONE

                    val postActivityResponse = result.data
                    val listActivities = postActivityResponse.listActivity?.filterNotNull() ?: emptyList()

                    val adapter = PostActivityAdapter(listActivities) { assignmentId ->
                        val intent = Intent(requireContext(), DetailAssignmentActivity::class.java)
                        intent.putExtra("ASSIGNMENT_ID", assignmentId)
                        startActivity(intent)
                    }
                    binding.rvPostActivity.adapter = adapter

                    val progressValue = postActivityResponse.progress?.toInt() ?: 0
                    binding.progressBar.progress = progressValue
                    binding.tvProgress.text = "$progressValue %"

                }
                is Result.Error -> {
                    binding.progressBarLoading.visibility = View.GONE
                    Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}