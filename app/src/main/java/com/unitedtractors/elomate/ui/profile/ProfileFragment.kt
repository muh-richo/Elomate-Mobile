package com.unitedtractors.elomate.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.unitedtractors.elomate.databinding.FragmentProfilBinding

class
ProfileFragment : Fragment() {

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

        binding.participantData.setOnClickListener{
            val intent = Intent(activity, ParticipantDataActivity::class.java)
            startActivity(intent)
        }

        binding.selfPeer.setOnClickListener{
            val intent = Intent(activity, AssessmentActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}