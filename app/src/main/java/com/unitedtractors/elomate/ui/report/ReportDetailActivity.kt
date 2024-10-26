package com.unitedtractors.elomate.ui.report

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

class ReportDetailActivity : AppCompatActivity() {

    private lateinit var horizontalBarChart: HorizontalBarChart
    private lateinit var radarChart1: RadarChart
    private lateinit var radarChart2: RadarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_detail)

        horizontalBarChart = findViewById(R.id.stacked_barChart)
        radarChart1 = findViewById(R.id.chart1)
        radarChart2 = findViewById(R.id.chart2)

        setupHorizontalStackedBarChart()
        setupRadarChart(radarChart1)
        setupSecondRadarChart(radarChart2)
    }

    private fun setupHorizontalStackedBarChart() {
        // Sample data for the stacked bar
        val barEntries = ArrayList<BarEntry>()

        val ValBar1 = 3f
        val ValBar2 = 4f
        val ValBar3 = 5f

        val val1 = ValBar1
        val val2 = ValBar2 - val1
        val val3 = ValBar3 - (val1 + val2)

        // Create BarEntry for a single bar with 3 stacked values
        barEntries.add(BarEntry(0f, floatArrayOf(val1, val2, val3)))

        // Create BarDataSet
        val barDataSet = BarDataSet(barEntries, "Sample Data")

        // Set the colors for each stack (adjust these colors according to your image)
        barDataSet.colors = listOf(
            ColorTemplate.rgb("#4285F4"), // Blue color
            ColorTemplate.rgb("#EA4335"), // Red color
            ColorTemplate.rgb("#CCCCCC")  // Light gray
        )

        // Enable drawing of values on top of the stacks
        barDataSet.setDrawValues(true)

        // Add a custom value formatter to display custom text instead of values
//        barDataSet.valueFormatter = object : ValueFormatter() {
//            override fun getBarStackedLabel(value: Float, entry: BarEntry?): String {
//                return when (value) {
//                    val1 -> ValBar1.toInt().toString()
//                    val2 -> ValBar2.toInt().toString()
//                    val3 -> ValBar3.toInt().toString()
//                    else -> ""
//                }
//            }
//        }

        // Create BarData and set it to the chart
        val barData = BarData(barDataSet)
        horizontalBarChart.data = barData

        // Optional customization
        horizontalBarChart.setDrawValueAboveBar(true)
        horizontalBarChart.setFitBars(true)
        horizontalBarChart.description = Description().apply { text = "" } // Remove description label
        horizontalBarChart.legend.isEnabled = false // Disable legend if not needed
        horizontalBarChart.axisLeft.isEnabled = false // Hide left axis
        horizontalBarChart.axisRight.isEnabled = false // Hide right axis
        horizontalBarChart.xAxis.isEnabled = false // Hide X axis

        horizontalBarChart.setExtraOffsets(0f, 0f, 0f, 0f)

        // Disable zooming and panning
        horizontalBarChart.setScaleEnabled(false) // Disable zooming
        horizontalBarChart.setPinchZoom(false) // Disable pinch zoom
        horizontalBarChart.isDragEnabled = false // Disable dragging (panning)
        horizontalBarChart.isDoubleTapToZoomEnabled = false // Disable double tap zoom

        // Refresh chart
        horizontalBarChart.invalidate()
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
