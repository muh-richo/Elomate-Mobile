package com.unitedtractors.elomate.ui.report

import android.R
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.unitedtractors.elomate.databinding.FragmentReportBinding
import com.unitedtractors.elomate.ui.home.ScheduleActivity

class
ReportFragment : Fragment() {

    private var _binding: FragmentReportBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(ReportViewModel::class.java)

        _binding = FragmentReportBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textReport
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        binding.viewDetail.setOnClickListener{
            val intent = Intent(activity, ReportDetailActivity::class.java)
            startActivity(intent)
        }

        setupSpinner()
        setupRadarChart()
        setupSecondRadarChart()

        return root
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
        // Define the array of items with the first item as a hint
        val phase = arrayOf("Pilih phase", "phase 1", "phase 2")
        val adapterPhase = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, phase)

        // Set the dropdown view resource
        adapterPhase.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set adapters for the Spinners
        binding.spinnerPhaseReport.adapter = adapterPhase

        // Optional: Set a default hint text color or behavior (if necessary)
        binding.spinnerPhaseReport.setSelection(0)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}