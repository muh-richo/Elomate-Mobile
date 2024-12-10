package com.unitedtractors.elomate.ui.report

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.response.AllDataItem
import com.unitedtractors.elomate.data.network.response.KirkPatrickResponse
import com.unitedtractors.elomate.databinding.FragmentReportBinding
import com.unitedtractors.elomate.databinding.TableRowsPlaceholderBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.report.detail.ReportDetailActivity

class ReportFragment : Fragment() {

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreference = UserPreference(requireContext())
        userModel = userPreference.getUser()

        binding.viewDetail.setOnClickListener{
            val intent = Intent(activity, ReportDetailActivity::class.java)
            startActivity(intent)
        }

        setupSpinner()
        getKirkpatrickReport()
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
                        val listCourse = reportResponse.listCourse

                        val dynamicRows = binding.dynamicRows

                        listCourse?.let {
                            dynamicRows.removeAllViews()

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

    private fun getKirkpatrickReport(){
        viewModel.getKirkpatrickReport("Bearer ${userModel.id}").observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {  }
                is Result.Success -> {
                    val response = result.data
                    setupRadarCharts(response)
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupRadarCharts(response: KirkPatrickResponse) {
        // Untuk kategori "Solution" - Peer Assessment
        response.peerAssessment?.allData?.find { it?.category == "SOLUTION" }?.let { peerSolutionData ->
            binding.tvSolutionTitle.text = "${peerSolutionData.category}"
            setupRadarChart(
                chart = binding.chart1,
                categoryData = peerSolutionData,
                label = "Peer"
            )
        }

        // Untuk kategori "Solution" - Self Assessment
        response.selfAssessment?.allData?.find { it?.category == "SOLUTION" }?.let { selfSolutionData ->
            setupRadarChart(
                chart = binding.chart1,
                categoryData = selfSolutionData,
                label = "Self"
            )
        }

        // Untuk kategori "8 BC" - Peer Assessment
        response.peerAssessment?.allData?.find { it?.category == "8 BC" }?.let { peerBCData ->
            binding.tvBehaviourCompetenciesTitle.text = "${peerBCData.category}"
            setupRadarChart(
                chart = binding.chart2,
                categoryData = peerBCData,
                label = "Peer"
            )
        }

        // Untuk kategori "8 BC" - Self Assessment
        response.selfAssessment?.allData?.find { it?.category == "8 BC" }?.let { selfBCData ->
            setupRadarChart(
                chart = binding.chart2,
                categoryData = selfBCData,
                label = "Self"
            )
        }
    }

    private fun setupRadarChart(
        chart: RadarChart,
        categoryData: AllDataItem,
        label: String
    ) {
        val categories = mutableListOf<String>()
        val scores = mutableListOf<RadarEntry>()

        categoryData.data?.forEach { assessment ->
            categories.add(assessment?.pointKirkpatrick ?: "Unknown")
            scores.add(RadarEntry(assessment?.averageScore?.toFloat() ?: 0f))
        }

        // Buat DataSet
        val dataSet = RadarDataSet(scores, label).apply {
            color = if (label.contains("Self")) {
                resources.getColor(com.unitedtractors.elomate.R.color.blue_1, null)
            } else {
                resources.getColor(com.unitedtractors.elomate.R.color.secondary_500, null)
            }
            fillColor = color
            setDrawFilled(true)
            lineWidth = 2f
        }

        // Gabungkan ke RadarData (jika sudah ada dataset lain)
        val radarData = chart.data ?: RadarData()
        radarData.addDataSet(dataSet)
        radarData.setValueTextSize(10f)

        // Tambahkan formatter untuk nilai integer
        radarData.setValueFormatter(object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        })

        chart.apply {
            data = radarData
            description.isEnabled = false
            setWebLineWidth(1f)
            setWebColor(resources.getColor(R.color.black, null))
            setWebLineWidthInner(0.5f)
            setWebColorInner(resources.getColor(R.color.black, null))
            setWebAlpha(100)

            xAxis.apply {
                valueFormatter = IndexAxisValueFormatter(categories)
                textSize = 10f
            }

            yAxis.apply {
                axisMinimum = 0f
                axisMaximum = 5f
                setDrawLabels(true)
                textSize = 10f
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return value.toInt().toString()
                    }
                }
            }

            invalidate()
        }
    }

}