package com.unitedtractors.elomate.ui.profile.riwayatpendidikan.add

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.databinding.FragmentAddEducationBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.profile.riwayatpendidikan.EducationViewModel

class AddEducationBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddEducationBinding

    private val viewModel by viewModels<EducationViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    companion object {
        const val REQUEST_KEY = "ADD_EDUCATION_RESULT"
        const val RESULT_KEY = "isSuccess"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddEducationBinding.inflate(inflater, container, false)

        userPreference = UserPreference(requireContext())
        userModel = userPreference.getUser()

        setupSpinner()

        binding.btnAdd.setOnClickListener {
            addEducation()
        }

        return binding.root
    }

    private fun setupSpinner() {
        var selectedJenjangStudi: String? = null

        // Spinner untuk Courses
        viewModel.getEducationLevel("Bearer ${userModel.token}").observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {}
                is Result.Success -> {
                    val response = result.data.map { it }

                    val adapterCourse =
                        ArrayAdapter(requireContext(), R.layout.simple_spinner_item, response)
                    adapterCourse.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                    binding.spinnerJenjangStudi.adapter = adapterCourse

                    binding.spinnerJenjangStudi.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                selectedJenjangStudi = response[position]
                            }
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                selectedJenjangStudi = null
                            }
                        }
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addEducation() {
        val university = binding.etNamaUniv.text
        val major = binding.etJurusan.text
        val degree = binding.spinnerJenjangStudi.selectedItem.toString()
        val graduationYear = binding.etTahunLulus.text

        if (university!!.isEmpty() || major!!.isEmpty() || degree.isEmpty() || graduationYear!!.isEmpty()) {
            Toast.makeText(requireContext(), "Isi semua data!", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.addEducation(
                "Bearer ${userModel.token}",
                university.toString(),
                major.toString(),
                degree,
                graduationYear.toString()
            ).observe(requireActivity()) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {  }
                        is Result.Success -> {
                            Toast.makeText(requireContext(), "Berhasil Menambahkan Pendidikan!", Toast.LENGTH_SHORT).show()

                            setFragmentResult(REQUEST_KEY, Bundle().apply {
                                putBoolean(RESULT_KEY, true)
                            })
                            dismiss()
                        }
                        is Result.Error -> {
                            Toast.makeText(requireContext(), "Gagal Menambahkan Pendidikan!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}