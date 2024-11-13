package com.unitedtractors.elomate.ui.profile.displayprofile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.response.UserResponse
import com.unitedtractors.elomate.databinding.FragmentProfileBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.auth.login.LoginActivity
import com.unitedtractors.elomate.ui.assessment.AssessmentActivity
import com.unitedtractors.elomate.ui.profile.changepassword.ChangePasswordActivity
import com.unitedtractors.elomate.ui.profile.editprofile.EditProfileActivity
import com.unitedtractors.elomate.ui.forum.ForumActivity
import com.unitedtractors.elomate.ui.profile.participant.ParticipantDataActivity
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private var userModel: User = User()
    private lateinit var userPreference: UserPreference

    private lateinit var userApi: UserResponse

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        userPreference = UserPreference(requireContext())
        userModel = userPreference.getUser()

        getCurrentUserApi()

        binding.apply {
            btnSelfPeer.setOnClickListener {
                val intent = Intent(activity, AssessmentActivity::class.java)
                startActivity(intent)
            }

            btnParticipantData.setOnClickListener {
                val intent = Intent(activity, ParticipantDataActivity::class.java)
                startActivity(intent)
            }

            btnForum.setOnClickListener {
                val intent = Intent(activity, ForumActivity::class.java)
                startActivity(intent)
            }

            btnEditProfile.setOnClickListener {
                val intent = Intent(activity, EditProfileActivity::class.java)
                startActivity(intent)
            }

            btnChangePassword.setOnClickListener {
                val intent = Intent(activity, ChangePasswordActivity::class.java)
                startActivity(intent)
            }

            btnLogout.setOnClickListener {
                logout()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun getCurrentUserApi() {
        viewModel.getCurrentUserApi("Bearer ${userModel.id}").observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE

                        userApi = result.data

                        binding.apply {
                            tvNamaLengkap.text = userApi.namaLengkap
                            tvNrp.text = userApi.nrp
                            tvPosisi.text = userApi.posisi
                            tvTempatTanggalLahir.text = buildString {
                                append(userApi.tempatLahir)
                                append(", ")
                                append(userApi.tanggalLahir)
                            }
                            tvAsalUniversitas.text = userApi.asalUniversitas
                            tvJurusan.text = userApi.jurusan
                            tvDomisili.text = userApi.domisili
                            tvNoHp.text = userApi.noHp
                            tvEmail.text = userApi.email
                        }
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE

//                        Log.e(TAG, "${R.string.failed_get_account} ${result.error}")
//                        showToast(getString(R.string.failed_get_account))
                    }
                }
            }
        }
    }

    private fun logout() {
        lifecycleScope.launch {
            userModel.id = ""
            userPreference.setUser(userModel)

            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clear back stack
            startActivity(intent)

            Toast.makeText(requireContext(), "Logout berhasil", Toast.LENGTH_SHORT).show()
        }
    }
}