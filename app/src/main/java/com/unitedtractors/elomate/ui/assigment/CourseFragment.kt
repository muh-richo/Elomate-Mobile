package com.unitedtractors.elomate.ui.assigment

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.databinding.FragmentCourseBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.auth.login.LoginActivity

class
CourseFragment : Fragment() {

    private lateinit var binding: FragmentCourseBinding
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
        binding = FragmentCourseBinding.inflate(layoutInflater)

        userPreference = UserPreference(requireContext())
        userModel = userPreference.getUser()

        binding.course1.setOnClickListener {
            val intent = Intent(activity, CourseActivity::class.java)
            startActivity(intent)
        }

        getCurrentUserApi()
        getCourses()

        // Initialize the Spinner
        setupSpinner()

        return binding.root
    }

    private fun getCurrentUserApi() {
        viewModel.getCurrentUserApi("Bearer ${userModel.id}").observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
//                        binding.progressHorizontal.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
//                        binding.progressHorizontal.visibility = View.GONE

                        val response = result.data
                        binding.tvHiUser.text = getString(com.unitedtractors.elomate.R.string.hi_user, response.namaLengkap)
                    }

                    is Result.Error -> {
//                        binding.progressHorizontal.visibility = View.GONE

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
    }

    private fun getCourses() {
        viewModel.getCourses("Bearer ${userModel.id}").observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
//                        binding.progressHorizontal.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
//                        binding.progressHorizontal.visibility = View.GONE

                        val response = result.data.courseResponse

                        val responseCourse = response!!.map {
                            it!!.namaCourse
                        }

//                        val responseMessage =
//                            if (response!!.isNotEmpty()) {
//                                val lastContent = response.size
//                                response[lastContent - 1]!!.namaCourse
//                            } else { "" }

                        binding.tvCourseName.text = responseCourse.toString()
                    }

                    is Result.Error -> {
//                        binding.progressHorizontal.visibility = View.GONE

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
    }

    private fun setupSpinner() {
        // Define the array of items
        val phases = arrayOf("Phase 1", "Phase 2", "Phase 3", "Phase 4", "Phase 10")
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, phases)

        val topic = arrayOf("General Development", "Orientasi Divisi", "BGMS", "NEOP")
        val adapter2 = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, topic)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner: Spinner = binding.spinnerDropdown
        val spinner2: Spinner = binding.spinnerDropdown2

        spinner.adapter = adapter
        spinner2.adapter = adapter2
    }


}