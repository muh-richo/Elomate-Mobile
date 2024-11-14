package com.unitedtractors.elomate.ui.assigment

import android.R
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
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.databinding.FragmentAssignmentBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.assignment.CourseAdapter
import com.unitedtractors.elomate.ui.assignment.CourseViewModel
import com.unitedtractors.elomate.ui.auth.login.LoginActivity

class AssignmentFragment : Fragment() {

    private var _binding: FragmentAssignmentBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentAssignmentBinding.inflate(inflater, container, false)

        userPreference = UserPreference(requireContext())
        userModel = userPreference.getUser()

        // Inisialisasi onClick untuk navigasi
//        binding.course1.setOnClickListener {
//            val intent = Intent(activity, CourseActivity::class.java)
//            startActivity(intent)
//        }

        getCurrentUserApi()
        setupRecyclerView()
        setupSpinner()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getCurrentUserApi() {
        viewModel.getCurrentUserApi("Bearer ${userModel.id}").observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    // Tampilkan progress jika diperlukan
                }
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

    private fun setupRecyclerView() {
        // Menambahkan layout manager untuk RecyclerView
        binding.rvCourse.layoutManager = LinearLayoutManager(requireContext())

        // Pastikan ProgressBar tidak terlihat saat tidak sedang loading
        binding.progressBar.visibility = View.GONE

        viewModel.getCourses("Bearer ${userModel.id}").observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    // Tampilkan progress bar - loading
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    // Sembunyikan progress bar dan tampilkan data saat sukses
                    binding.progressBar.visibility = View.GONE
                    val courses = result.data
                    binding.rvCourse.adapter = CourseAdapter(courses)
                }
                is Result.Error -> {
                    // Sembunyikan progress bar dan tampilkan pesan error
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun setupSpinner() {
        // Inisialisasi Spinner pertama
        val phases = arrayOf("Phase 1", "Phase 2", "Phase 3", "Phase 4", "Phase 10")
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, phases)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDropdown.adapter = adapter

        // Inisialisasi Spinner kedua
        val topics = arrayOf("General Development", "Orientasi Divisi", "BGMS", "NEOP")
        val adapter2 = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, topics)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDropdown2.adapter = adapter2
    }
}
