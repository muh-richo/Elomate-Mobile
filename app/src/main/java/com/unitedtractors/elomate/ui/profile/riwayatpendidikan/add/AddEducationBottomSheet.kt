package com.unitedtractors.elomate.ui.profile.riwayatpendidikan.add

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

        binding.btnAdd.setOnClickListener {
            addEducation()
        }

        return binding.root
    }

    private fun addEducation() {
        val university = binding.etNamaUniv.text
        val major = binding.etJurusan.text
        val degree = binding.etJenjangStudi.text
        val graduationYear = binding.etTahunLulus.text

        if (university!!.isEmpty() || major!!.isEmpty() || degree!!.isEmpty() || graduationYear!!.isEmpty()) {
            Toast.makeText(requireContext(), "Isi semua data!", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.addEducation(
                "Bearer ${userModel.id}",
                university.toString(),
                major.toString(),
                degree.toString(),
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