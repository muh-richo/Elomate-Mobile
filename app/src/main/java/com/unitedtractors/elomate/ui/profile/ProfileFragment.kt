package com.unitedtractors.elomate.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.databinding.FragmentProfilBinding
import com.unitedtractors.elomate.ui.auth.login.LoginActivity
import com.unitedtractors.elomate.ui.profile.forum.ForumActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profilViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textReport
//        profilViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        // Initialize UserPreference
        val userPreference = UserPreference(requireContext())

        binding.logout.setOnClickListener {
            // Clear the auth token on logout
            userPreference.clearAuthToken()

            // Navigate back to LoginActivity
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clear back stack
            startActivity(intent)
        }

        binding.participantData.setOnClickListener{
            val intent = Intent(activity, ParticipantDataActivity::class.java)
            startActivity(intent)
        }

        binding.selfPeer.setOnClickListener{
            val intent = Intent(activity, AssessmentActivity::class.java)
            startActivity(intent)
        }

        binding.forum.setOnClickListener{
            val intent = Intent(activity, ForumActivity::class.java)
            startActivity(intent)
        }

        binding.editProfile.setOnClickListener{
            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)
        }

        binding.changePassword.setOnClickListener{
            val intent = Intent(activity, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}