package com.unitedtractors.elomate.ui.mentoring.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.databinding.FragmentCreateBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.mentoring.MentoringViewModel

class CreateFragment : Fragment() {

    private lateinit var binding: FragmentCreateBinding
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
        binding = FragmentCreateBinding.inflate(layoutInflater)

        userPreference = UserPreference(requireContext())
        userModel = userPreference.getUser()

        setupSpinner()
        binding.btnAdd.setOnClickListener {
            addMentoring()
        }
        return binding.root
    }

    private fun addMentoring() {
        val etDate = binding.etDate.text
        val etStartTime = binding.etTimeStart.text
        val etEndTime = binding.etTimeEnd.text

        if (etDate!!.isEmpty() && etStartTime!!.isEmpty() && etEndTime!!.isEmpty()) {
            Toast.makeText(requireContext(), "Silahkan isi kolom yang ada", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.createMentoring(
                "Bearer ${userModel.id}",
                binding.spinnerCourse.selectedItem.toString(),
                binding.etDate.text.toString(),
                binding.etTimeStart.text.toString(),
                binding.etTimeEnd.text.toString(),
                binding.spinnerMethod.selectedItem.toString(),
                binding.spinnerType.selectedItem.toString()
                ).observe(viewLifecycleOwner) { result ->
                    if (result != null) {
                        when (result) {
                            is Result.Loading -> {
                                // Tampilkan loading jika diperlukan
                            }
                            is Result.Success -> {
                                Toast.makeText(requireContext(), "Mentoring berhasil ditambahkan", Toast.LENGTH_SHORT).show()

//                                val navController = (requireActivity().supportFragmentManager.findFragmentById(R.id.nav_graph) as NavHostFragment).navController
//                                navController.navigate(R.id.action_fragment_create_to_upcomingFragment)
                            }
                            is Result.Error -> {
                                Toast.makeText(requireContext(), "Mentoring gagal ditambahkan", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            }
        }

    }

    private fun setupSpinner() {
        var selectedMethod: String? = null
        var selectedType: String? = null
        var selectedCourse: String? = null

        // Spinner untuk Courses
        viewModel.getAllCourses("Bearer ${userModel.id}").observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {  }
                is Result.Success -> {
                    val response = result.data.map { it.namaCourse }

                    val adapterCourse = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, response)
                    adapterCourse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinnerCourse.adapter = adapterCourse

                    binding.spinnerCourse.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            selectedCourse = response[position]
                            // Lakukan sesuatu dengan `selectedCourse` jika diperlukan
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            selectedCourse = null
                        }
                    }
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), "No Course Found", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Spinner untuk Method Mentoring
        viewModel.getMethodMentoring("Bearer ${userModel.id}").observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> { /* Tampilkan loading jika perlu */ }
                is Result.Success -> {
                    val methods = result.data

                    val adapterMethod = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, methods)
                    adapterMethod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinnerMethod.adapter = adapterMethod

                    binding.spinnerMethod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            selectedMethod = methods[position]
                            // Lakukan sesuatu dengan `selectedMethod` jika diperlukan
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            selectedMethod = null
                        }
                    }
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), "No Method Found", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Spinner untuk Type Mentoring
        viewModel.getTypeMentoring("Bearer ${userModel.id}").observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {  }
                is Result.Success -> {
                    val types = result.data

                    val adapterType = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, types)
                    adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinnerType.adapter = adapterType

                    binding.spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            selectedType = types[position]
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            selectedType = null
                        }
                    }
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), "No Type Found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}