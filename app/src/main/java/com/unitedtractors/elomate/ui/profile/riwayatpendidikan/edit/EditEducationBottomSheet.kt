package com.unitedtractors.elomate.ui.profile.riwayatpendidikan.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.databinding.FragmentEditEducationBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.profile.riwayatpendidikan.EducationViewModel

class EditEducationBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEditEducationBinding

    private val viewModel by viewModels<EducationViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    companion object {
        const val ARG_EDUCATION_ID = "EDUCATION_ID"
        const val REQUEST_KEY = "EDIT_EDUCATION_RESULT"
        const val RESULT_KEY = "isSuccess"

        fun newInstance(educationId: Int): EditEducationBottomSheet {
            val fragment = EditEducationBottomSheet()
            val bundle = Bundle()
            bundle.putInt(ARG_EDUCATION_ID, educationId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEditEducationBinding.inflate(inflater, container, false)

        userPreference = UserPreference(requireContext())
        userModel = userPreference.getUser()

        val educationId = arguments?.getInt(ARG_EDUCATION_ID) ?: -1

        binding.apply {
            btnSave.setOnClickListener {
                editEducation(educationId)
            }
            btnDelete.setOnClickListener {
                deleteEducation(educationId)
            }
        }

        return binding.root
    }

    private fun editEducation(educationId: Int) {
        val etUniversitas = binding.etNamaUniv.text
        val etJurusan = binding.etJurusan.text
        val etJenjangStudi = binding.etJenjangStudi.text
        val etTahunLulus = binding.etTahunLulus.text

        viewModel.updateEducation(
            "Bearer ${userModel.id}",
            educationId,
            etUniversitas.toString(),
            etJurusan.toString(),
            etJenjangStudi.toString(),
            etTahunLulus.toString()
        ).observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {  }
                    is Result.Success -> {
                        Toast.makeText(requireContext(), "Berhasil Mengedit Data!", Toast.LENGTH_SHORT).show()

                        setFragmentResult(REQUEST_KEY, Bundle().apply {
                            putBoolean(RESULT_KEY, true)
                        })
                        dismiss()
                    }
                    is Result.Error -> {
                        Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun deleteEducation(educationId: Int) {
        viewModel.deleteEducation("Bearer ${userModel.id}", educationId).observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {  }
                    is Result.Success -> {
                        Toast.makeText(requireContext(), "Berhasil Menghapus Data!", Toast.LENGTH_SHORT).show()

                        setFragmentResult(REQUEST_KEY, Bundle().apply {
                            putBoolean(RESULT_KEY, true)
                        })
                        dismiss()
                    }
                    is Result.Error -> {
                        Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}