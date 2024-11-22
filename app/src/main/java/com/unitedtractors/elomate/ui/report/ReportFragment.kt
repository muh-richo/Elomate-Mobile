package com.unitedtractors.elomate.ui.report

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.databinding.FragmentReportBinding
import com.unitedtractors.elomate.databinding.TableRowsPlaceholderBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.auth.login.LoginActivity
import com.unitedtractors.elomate.ui.report.detail.ReportDetailActivity

class
ReportFragment : Fragment() {

    private lateinit var binding: FragmentReportBinding
    private val viewModel by viewModels<ReportViewModel>{
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportBinding.inflate(layoutInflater)

        userPreference = UserPreference(requireContext())
        userModel = userPreference.getUser()

        binding.viewDetail.setOnClickListener{
            val intent = Intent(activity, ReportDetailActivity::class.java)
            startActivity(intent)
        }

        getCurrentUserApi()
        setupSpinner()
        setupRadarChart(
            binding.chart1,
            labels = arrayOf("Networking", "Organize", "Leadership", "Uniqueness", "Totality", "Innovative", "Open-Mind"),
            selfData = floatArrayOf(4f, 3f, 5f, 4f, 2f, 3f, 4f),
            rekanData = floatArrayOf(3f, 4f, 4f, 3f, 3f, 4f, 3f)
        )
        setupRadarChart(
            binding.chart2,
            labels = arrayOf("LM", "AJ", "CF", "DC", "TW", "PDA", "IS", "VBS"),
            selfData = floatArrayOf(4f, 4f, 3f, 5f, 4f, 3f, 2f, 5f),
            rekanData = floatArrayOf(3f, 3f, 4f, 4f, 3f, 4f, 5f, 3f)
        )

        return binding.root
    }

    private fun getCurrentUserApi() {
        viewModel.getCurrentUserApi("Bearer ${userModel.id}").observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> { }
                    is Result.Success -> {
                        val response = result.data
//                        binding.tvUserName.text = response.namaLengkap
//                        binding.tvPosisi.text = response.posisi
                    }

                    is Result.Error -> {
                        val errorMessage = result.error.message
                        handleError(errorMessage)
                    }
                }
            }
        }
    }

    private fun setupSpinner() {
        viewModel.getPhaseUser("Bearer ${userModel.id}").observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> { }
                is Result.Success -> {
                    val phases = result.data.map { it.namaPhase }
                    val phaseIds = result.data.map { it.phaseId }

                    val adapterPhase = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, phases)
                    adapterPhase.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                    binding.spinnerPhase.adapter = adapterPhase

                    binding.spinnerPhase.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            val selectedPhaseId = phaseIds[position]

                            if (selectedPhaseId != null) {
                                viewModel.getTopicUser("Bearer ${userModel.id}", selectedPhaseId).observe(viewLifecycleOwner) { topicResult ->
                                    when (topicResult) {
                                        is Result.Loading -> { }
                                        is Result.Success -> {
                                            val topics = topicResult.data.map { it.namaTopik }
                                            val topicIds = topicResult.data.map { it.topikId }

                                            val topicAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, topics)
                                            topicAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                                            binding.spinnerTopic.adapter = topicAdapter

                                            binding.spinnerTopic.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                                    val selectedTopicId = topicIds[position]
                                                    if (selectedTopicId != null) {
                                                        setupTableReport(selectedPhaseId, selectedTopicId)
                                                    }
                                                }

                                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                                    TODO("Not yet implemented")
                                                }
                                            }
                                        }
                                        is Result.Error -> {
                                            Toast.makeText(requireContext(), topicResult.error.message, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                        }
                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }
                    }
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), "No Course Found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupTableReport(phaseId: Int, topicId: Int) {
        viewModel.getReportUser("Bearer ${userModel.id}", phaseId, topicId).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> { }
                    is Result.Success -> {
                        val reportResponse = result.data
                        val listCourse = reportResponse.listCourse // List<ListCourseItem>

                        val dynamicRows = binding.dynamicRows

                        // Pastikan listCourse tidak null dan memiliki data
                        listCourse?.let {
                            // Kosongkan dynamic rows sebelum menambah data baru
                            dynamicRows.removeAllViews()

                            // Menambahkan baris baru untuk setiap item di listCourse
                            it.forEachIndexed { index, course ->
                                val tableRowBinding = TableRowsPlaceholderBinding.inflate(LayoutInflater.from(context), null, false)

                                // Set data ke TextView dalam TableRow
                                tableRowBinding.tvNo.text = (index + 1).toString()
                                tableRowBinding.tvCourse.text = course?.namaCourse ?: "No Course"
                                tableRowBinding.tvNilai.text = course?.nilaiTotalCourse?.toString() ?: "-"
                                tableRowBinding.tvStatus.text = course?.status ?: "Tidak ada status"

                                tableRowBinding.root.setBackgroundResource(com.unitedtractors.elomate.R.drawable.bg_table_row)

//                                // Menambahkan warna latar belakang berdasarkan indeks ganjil/genap
//                                if (index % 2 == 0) {
//                                    tableRowBinding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), com.unitedtractors.elomate.R.color.shades_50))
//                                } else {
//                                    tableRowBinding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), com.unitedtractors.elomate.R.color.neutral_200))
//                                }

                                // Tambahkan baris ke dynamicRows container
                                dynamicRows.addView(tableRowBinding.root)

                                binding.tvAverageNilai.text = reportResponse.rataRataSemuaCourse.toString()
                            }
                        }
                    }
                    is Result.Error -> {
                        Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupRadarChart(chart: RadarChart, labels: Array<String>, selfData: FloatArray, rekanData: FloatArray) {
        val selfEntries = selfData.map { RadarEntry(it) }
        val rekanEntries = rekanData.map { RadarEntry(it) }

        val selfDataSet = RadarDataSet(selfEntries, "Self").apply {
            color = Color.BLUE
            lineWidth = 2f
            fillColor = Color.BLUE
            setDrawFilled(true)
        }

        val rekanDataSet = RadarDataSet(rekanEntries, "Rekan Kerja").apply {
            color = Color.RED
            lineWidth = 2f
            fillColor = Color.RED
            setDrawFilled(true)
        }

        val radarData = RadarData(selfDataSet, rekanDataSet)
        chart.data = radarData

        chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        chart.yAxis.apply {
            axisMinimum = 0f
            axisMaximum = 5f
            setDrawLabels(false)
        }
        chart.legend.apply {
            isEnabled = true
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        }
        chart.description.isEnabled = false
        chart.invalidate()
    }

    private fun handleError(message: String?) {
        if (message == "timeout") {
            userModel.id = ""
            userPreference.setUser(userModel)
            Toast.makeText(requireContext(), "Sesi telah berakhir. Silakan login kembali.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        } else {
            Toast.makeText(requireContext(), "Error: $message", Toast.LENGTH_SHORT).show()
        }
    }
}