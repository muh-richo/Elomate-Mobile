package com.unitedtractors.elomate.ui.report

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.databinding.ActivityAnnouncementBinding
import com.unitedtractors.elomate.databinding.ActivityReportDetailBinding

class ReportDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportDetailBinding

    private lateinit var radarChart1: RadarChart
    private lateinit var radarChart2: RadarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityReportDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Change the status bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300) // Replace with your color resource

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        radarChart1 = findViewById(R.id.chart1)
        radarChart2 = findViewById(R.id.chart2)

        setupRadarChart(radarChart1)
        setupSecondRadarChart(radarChart2)
    }

    private fun setupRadarChart(chart: RadarChart) {
        val labels = arrayOf("Networking", "Organize", "Leadership", "Uniqueness", "Totality", "Innovative", "Open-Mind")
        val selfData = floatArrayOf(4f, 3f, 5f, 4f, 2f, 3f, 4f)
        val rekanData = floatArrayOf(3f, 4f, 4f, 3f, 3f, 4f, 3f)

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

        chart.data = RadarData(selfDataSet, rekanDataSet)
        chart.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(labels)
        }
        chart.yAxis.apply {
            axisMinimum = 0f
            axisMaximum = 5f
            setDrawLabels(false)
        }
        chart.legend.apply {
            isEnabled = true
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            verticalAlignment = Legend.LegendVerticalAlignment.TOP
            orientation = Legend.LegendOrientation.HORIZONTAL
        }
        chart.description.isEnabled = false
        chart.invalidate()
    }

    private fun setupSecondRadarChart(chart: RadarChart) {
        val labels = arrayOf("LM", "AJ", "CF", "DC", "TW", "PDA", "IS", "VBS")
        val selfData = floatArrayOf(4f, 4f, 3f, 5f, 4f, 3f, 2f, 5f)
        val rekanData = floatArrayOf(3f, 3f, 4f, 4f, 3f, 4f, 5f, 3f)

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

        chart.data = RadarData(selfDataSet, rekanDataSet)
        chart.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(labels)
        }
        chart.yAxis.apply {
            axisMinimum = 0f
            axisMaximum = 5f
            setDrawLabels(false)
        }
        chart.legend.apply {
            isEnabled = true
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            verticalAlignment = Legend.LegendVerticalAlignment.TOP
            orientation = Legend.LegendOrientation.HORIZONTAL
        }
        chart.description.isEnabled = false
        chart.invalidate()
    }
}
