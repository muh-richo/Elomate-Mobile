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
import android.R
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

    private lateinit var courseAdapter: CourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAssignmentBinding.inflate(layoutInflater)

        userPreference = UserPreference(requireContext())
        userModel = userPreference.getUser()

        getCurrentUserApi()
        setupRecyclerView()
        setupSpinner()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
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
        binding.rvCourse.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getCourses("Bearer ${userModel.id}").observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val courses = result.data
                    binding.rvCourse.adapter = CourseAdapter(courses)
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupSpinner() {
        // Observasi LiveData untuk mendapatkan data dari API
        viewModel.getPhaseUser("Bearer ${userModel.id}").observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {

                }
                is Result.Success -> {
                    // Map data dari Response API ke dalam List String atau langsung ambil field tertentu
                    val phases = result.data.map { it.namaPhase } // ganti `phaseName` dengan field yang sesuai di `PhaseResponse`

                    // Inisialisasi Spinner Adapter dengan data dari API
                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, phases)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinnerDropdown.adapter = adapter
                }
                is Result.Error -> {
                    // Tangani error (misalnya, tampilkan pesan kesalahan)
                    Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

//    private fun setupSpinner() {
//        // Inisialisasi Spinner pertama
//        val phases = arrayOf("Phase 1", "Phase 2", "Phase 3", "Phase 4", "Phase 10")
//        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, phases)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        binding.spinnerDropdown.adapter = adapter
//
//        // Inisialisasi Spinner kedua
//        val topics = arrayOf("General Development", "Orientasi Divisi", "BGMS", "NEOP")
//        val adapter2 = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, topics)
//        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        binding.spinnerDropdown2.adapter = adapter2
//    }
}
