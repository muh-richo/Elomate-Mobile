package com.unitedtractors.elomate.ui.profile.riwayatpendidikan

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.adapter.EducationAdapter
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.databinding.ActivityEducationBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.profile.riwayatpendidikan.add.AddEducationBottomSheet
import com.unitedtractors.elomate.ui.profile.riwayatpendidikan.edit.EditEducationBottomSheet

class EducationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEducationBinding

    private val viewModel by viewModels<EducationViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEducationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        userPreference = UserPreference(this)
        userModel = userPreference.getUser()

        loadEducationData()

        supportFragmentManager.setFragmentResultListener(
            AddEducationBottomSheet.REQUEST_KEY,
            this
        ) { _, result ->
            val isSuccess = result.getBoolean(AddEducationBottomSheet.RESULT_KEY, false)
            if (isSuccess) {
                loadEducationData()
            }
        }

        supportFragmentManager.setFragmentResultListener(
            EditEducationBottomSheet.REQUEST_KEY,
            this
        ) { _, bundle ->
            val isDataUpdated = bundle.getBoolean(EditEducationBottomSheet.RESULT_KEY, false)
            if (isDataUpdated) {
                loadEducationData()
            }
        }

        binding.icBack.setOnClickListener {
            finish()
        }

        binding.apply {
            icBack.setOnClickListener {
                finish()
            }
            btnAddEducation.setOnClickListener {
                val addEducationBottomSheet = AddEducationBottomSheet()
                addEducationBottomSheet.show(supportFragmentManager, "AddEducationBottomSheet")
            }
        }

    }

    private fun loadEducationData() {
        binding.rvRiwatatPendidikan.layoutManager = LinearLayoutManager(this)

        viewModel.getEducation("Bearer ${userModel.token}").observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE

                        val educationData = result.data

                        val adapter = EducationAdapter(educationData) { educationId ->
                            val editEducationBottomSheet = EditEducationBottomSheet.newInstance(educationId)
                            editEducationBottomSheet.show(supportFragmentManager, "EditEducationBottomSheet")
                        }
                        binding.rvRiwatatPendidikan.adapter = adapter
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}