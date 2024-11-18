package com.unitedtractors.elomate.ui.assigment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.AdapterView
import com.unitedtractors.elomate.adapter.CourseAdapter
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.databinding.FragmentAssignmentBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.assignment.CourseViewModel
import com.unitedtractors.elomate.ui.auth.login.LoginActivity

class AssignmentFragment : Fragment() {

    private lateinit var binding: FragmentAssignmentBinding
    private val viewModel by viewModels<CourseViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAssignmentBinding.inflate(layoutInflater)

        userPreference = UserPreference(requireContext())
        userModel = userPreference.getUser()

        getCurrentUserApi()

        setupSpinner()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun getCurrentUserApi() {
        viewModel.getCurrentUserApi("Bearer ${userModel.id}").observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> { }
                is Result.Success -> {
                    val response = result.data
                    binding.tvHiUser.text = getString(com.unitedtractors.elomate.R.string.hi_user, response.namaLengkap)
                }
                is Result.Error -> {
                    val errorMessage = result.error.message
                    if (errorMessage == "timeout") {
                        userModel.id = ""
                        userPreference.setUser(userModel)
                        Toast.makeText(requireContext(), "Sesi telah berakhir. Silakan login kembali.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView(token: String, phaseId: Int, topicId: Int) {
        binding.rvCourse.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getCourseByPhaseIdTopicId(token, phaseId, topicId).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val courses = result.data

                    val adapter = CourseAdapter(courses) { courseId ->
                        val intent = Intent(requireContext(), CourseActivity::class.java)
                        intent.putExtra("COURSE_ID", courseId) // Mengirimkan courseId melalui Intent
                        startActivity(intent)
                    }
                    binding.rvCourse.adapter = adapter
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupSpinner() {
        // Spinner Phase
        viewModel.getPhaseUser("Bearer ${userModel.id}").observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {}
                is Result.Success -> {
                    // Ambil nama phase untuk Spinner pertama
                    val phases = result.data.map { it.namaPhase }
                    val phaseIds = result.data.map { it.phaseId }

                    // Inisialisasi Adapter Spinner dengan data phase dari API
                    val adapterPhase = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, phases)
                    adapterPhase.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinnerDropdown.adapter = adapterPhase

                    // Listener ketika item pada Spinner Phase dipilih
                    binding.spinnerDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                            // Ambil phaseId dari item yang dipilih
                            val selectedPhaseId = phaseIds[position]

                            // Panggil API untuk mendapatkan topik berdasarkan phaseId yang dipilih
                            if (selectedPhaseId != null) {
                                viewModel.getTopicUser("Bearer ${userModel.id}", selectedPhaseId).observe(viewLifecycleOwner) { topicResult ->
                                    when (topicResult) {
                                        is Result.Loading -> { }

                                        is Result.Success -> {
                                            // Ambil nama topik untuk Spinner kedua
                                            val topics = topicResult.data.map { it.namaTopik }
                                            val topicIds = topicResult.data.map { it.topikId }

                                            // Inisialisasi Adapter Spinner kedua dengan data topik dari API
                                            val topicAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, topics)
                                            topicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                            binding.spinnerDropdown2.adapter = topicAdapter

                                            // Listener untuk Spinner Topic
                                            binding.spinnerDropdown2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                                override fun onItemSelected(parent: AdapterView<*>, view: View?, topicPosition: Int, id: Long) {
                                                    val selectedTopicId = topicIds[topicPosition]
                                                    if (selectedTopicId != null) {
                                                        setupRecyclerView("Bearer ${userModel.id}", selectedPhaseId, selectedTopicId)
                                                    }
                                                }

                                                override fun onNothingSelected(parent: AdapterView<*>) {}
                                            }
                                        }

                                        is Result.Error -> {
                                            Toast.makeText(requireContext(), topicResult.error.message, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }
                    }
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), "No Course Found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}