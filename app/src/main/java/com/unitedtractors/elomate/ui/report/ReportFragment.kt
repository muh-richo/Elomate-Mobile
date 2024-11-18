package com.unitedtractors.elomate.ui.report

import android.R
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.databinding.FragmentReportBinding
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
        setupRadarChart()
        setupSecondRadarChart()

        return binding.root
    }

    private fun getCurrentUserApi() {
        viewModel.getCurrentUserApi("Bearer ${userModel.id}").observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> { }
                    is Result.Success -> {
                        val response = result.data
                        binding.tvUserName.text = response.namaLengkap
                        binding.tvPosisi.text = response.posisi
                        binding.tvUsername.text = response.namaLengkap
                    }

                    is Result.Error -> {
                        val errorMessage = result.error.message

                        if (errorMessage == "timeout") {
                            userModel.id = ""
                            userPreference.setUser(userModel)

                            Toast.makeText(requireContext(), "Sesi telah berakhir. Silakan login kembali.", Toast.LENGTH_SHORT).show()

                            val intent = Intent(requireContext(), LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun setupRadarChart() {
        val chart: RadarChart = binding.chart1

        // Labels for the first chart
        val labels = arrayOf("Networking", "Organize", "Leadership", "Uniqueness", "Totality", "Innovative", "Open-Mind")
        val selfData = floatArrayOf(4f, 3f, 5f, 4f, 2f, 3f, 4f)
        val rekanData = floatArrayOf(3f, 4f, 4f, 3f, 3f, 4f, 3f)

        // Create Radar Entries
        val selfEntries = ArrayList<RadarEntry>()
        val rekanEntries = ArrayList<RadarEntry>()

        for (i in selfData.indices) {
            selfEntries.add(RadarEntry(selfData[i]))
            rekanEntries.add(RadarEntry(rekanData[i]))
        }

        // Create datasets
        val selfDataSet = RadarDataSet(selfEntries, "Self")
        selfDataSet.color = Color.BLUE
        selfDataSet.lineWidth = 2f
        selfDataSet.fillColor = Color.BLUE
        selfDataSet.setDrawFilled(true)

        val rekanDataSet = RadarDataSet(rekanEntries, "Rekan Kerja")
        rekanDataSet.color = Color.RED
        rekanDataSet.lineWidth = 2f
        rekanDataSet.fillColor = Color.RED
        rekanDataSet.setDrawFilled(true)

        // RadarData object
        val radarData = RadarData(selfDataSet, rekanDataSet)
        chart.data = radarData

        // Customize X-Axis (Labels)
        val xAxis: XAxis = chart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)

        // Customize Y-Axis
        val yAxis: YAxis = chart.yAxis
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 5f
        yAxis.setDrawLabels(false)

        // Customize Legend
        val legend: Legend = chart.legend
        legend.isEnabled = true
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.orientation = Legend.LegendOrientation.HORIZONTAL

        // Remove or disable description label
        chart.description.isEnabled = false

        // Refresh chart
        chart.invalidate()
    }

    private fun setupSecondRadarChart() {
        val chart: RadarChart = binding.chart2

        // Labels for the second chart
        val labels = arrayOf("LM", "AJ", "CF", "DC", "TW", "PDA", "IS", "VBS")
        val selfData = floatArrayOf(4f, 4f, 3f, 5f, 4f, 3f, 2f, 5f)
        val rekanData = floatArrayOf(3f, 3f, 4f, 4f, 3f, 4f, 5f, 3f)

        // Create Radar Entries
        val selfEntries = ArrayList<RadarEntry>()
        val rekanEntries = ArrayList<RadarEntry>()

        for (i in selfData.indices) {
            selfEntries.add(RadarEntry(selfData[i]))
            rekanEntries.add(RadarEntry(rekanData[i]))
        }

        // Create datasets
        val selfDataSet = RadarDataSet(selfEntries, "Self")
        selfDataSet.color = Color.BLUE
        selfDataSet.lineWidth = 2f
        selfDataSet.fillColor = Color.BLUE
        selfDataSet.setDrawFilled(true)

        val rekanDataSet = RadarDataSet(rekanEntries, "Rekan Kerja")
        rekanDataSet.color = Color.RED
        rekanDataSet.lineWidth = 2f
        rekanDataSet.fillColor = Color.RED
        rekanDataSet.setDrawFilled(true)

        // RadarData object
        val radarData = RadarData(selfDataSet, rekanDataSet)
        chart.data = radarData

        // Customize X-Axis (Labels)
        val xAxis: XAxis = chart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)

        // Customize Y-Axis
        val yAxis: YAxis = chart.yAxis
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 5f
        yAxis.setDrawLabels(false)

        // Customize Legend
        val legend: Legend = chart.legend
        legend.isEnabled = true
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.orientation = Legend.LegendOrientation.HORIZONTAL

        // Remove or disable description label
        chart.description.isEnabled = false

        // Refresh chart
        chart.invalidate()
    }


    private fun setupSpinner() {
        viewModel.getPhaseUser("Bearer ${userModel.id}").observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> { }
                is Result.Success -> {
                    val phases = result.data.map { it.namaPhase }

                    val adapterPhase = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, phases)
                    adapterPhase.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                    binding.spinnerPhase.adapter = adapterPhase


                }
                is Result.Error -> {
                    val errorMessage = result.error.message
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
    }
}