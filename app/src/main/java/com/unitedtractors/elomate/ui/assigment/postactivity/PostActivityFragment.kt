package com.unitedtractors.elomate.ui.assigment.postactivity

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
        binding = FragmentPostBinding.inflate(layoutInflater)

        userPreference = UserPreference(requireContext())
        userModel = userPreference.getUser()

        val courseId = arguments?.getInt("COURSE_ID", 0) ?: 0

        if (courseId != 0) {
            setupRecyclerView("Bearer ${userModel.id}", courseId)
        } else {
            Toast.makeText(requireContext(), "Course ID not found", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun setupRecyclerView(token: String, courseId: Int) {
        binding.rvPostActivity.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getPostActivityByCourseId(token, courseId).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBarLoading.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBarLoading.visibility = View.GONE

//                    binding.tvProgress.text = result.data.

                    val postActivityList = result.data
                    val adapter = PostActivityAdapter(postActivityList) { assignmentId ->
                        val intent = Intent(requireContext(), DetailAssignmentActivity::class.java)
                        intent.putExtra("ASSIGNMENT_ID", assignmentId)
                        startActivity(intent)
                    }
                    binding.rvPostActivity.adapter = adapter
                }
                is Result.Error -> {
                    binding.progressBarLoading.visibility = View.GONE
                    Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}