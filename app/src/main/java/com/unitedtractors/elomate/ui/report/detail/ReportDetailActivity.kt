package com.unitedtractors.elomate.ui.report.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.adapter.KirkpatrickDetailAdapter
import com.unitedtractors.elomate.data.local.user.User
import com.unitedtractors.elomate.data.local.user.UserPreference
import com.unitedtractors.elomate.data.network.Result
import com.unitedtractors.elomate.data.network.response.AllDataItem
import com.unitedtractors.elomate.data.network.response.KirkPatrickResponse
import com.unitedtractors.elomate.databinding.ActivityReportDetailBinding
import com.unitedtractors.elomate.ui.ViewModelFactory
import com.unitedtractors.elomate.ui.report.ReportViewModel

class ReportDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportDetailBinding

    private val viewModel by viewModels<ReportViewModel>{
        ViewModelFactory.getInstance(this)
    }

    private lateinit var userPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityReportDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_300)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        userPreference = UserPreference(this)
        userModel = userPreference.getUser()

        getKirkpatrickReport()
        getKirkpatrickDetail()

        binding.icBack.setOnClickListener {
            finish()
        }
    }

    private fun getKirkpatrickReport(){
        viewModel.getKirkpatrickReport("Bearer ${userModel.id}").observe(this) { result ->
            when (result) {
                is Result.Loading -> {  }
                is Result.Success -> {
                    val response = result.data
                    setupRadarCharts(response)
                }
                is Result.Error -> {
                    Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getKirkpatrickDetail() {
        viewModel.getKirkpatrickDetail("Bearer ${userModel.id}").observe(this) { result ->
            when (result) {
                is Result.Loading -> {  }
                is Result.Success -> {
                    val data = result.data.kirkpatrickDetail

                    val adapter =KirkpatrickDetailAdapter(data)
                    binding.rvDetailKirkpatrick.layoutManager = LinearLayoutManager(this)
                    binding.rvDetailKirkpatrick.adapter = adapter
                }
                is Result.Error -> {
                    Toast.makeText(this, "Error: ${result.error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupRadarCharts(response: KirkPatrickResponse) {
        response.peerAssessment?.allData?.find { it?.category == "SOLUTION" }?.let { peerSolutionData ->
            binding.tvSolutionTitle.text = "${peerSolutionData.category}"
            setupRadarChart(
                chart = binding.chart1,
                categoryData = peerSolutionData,
                label = "Peer"
            )
        }

        response.selfAssessment?.allData?.find { it?.category == "SOLUTION" }?.let { selfSolutionData ->
            setupRadarChart(
                chart = binding.chart1,
                categoryData = selfSolutionData,
                label = "Self"
            )
        }

        response.peerAssessment?.allData?.find { it?.category == "8 BC" }?.let { peerBCData ->
            binding.tvBehaviourCompetenciesTitle.text = "${peerBCData.category}"
            setupRadarChart(
                chart = binding.chart2,
                categoryData = peerBCData,
                label = "Peer"
            )
        }

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
                resources.getColor(R.color.blue_1, null)
            } else {
                resources.getColor(R.color.secondary_500, null)
            }
            fillColor = color
            setDrawFilled(true)
            lineWidth = 2f
        }

        val radarData = chart.data ?: RadarData()
        radarData.addDataSet(dataSet)
        radarData.setValueTextSize(10f)

        radarData.setValueFormatter(object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        })

        chart.apply {
            data = radarData
            description.isEnabled = false
            setWebLineWidth(1f)
            setWebColor(resources.getColor(android.R.color.black, null))
            setWebLineWidthInner(0.5f)
            setWebColorInner(resources.getColor(android.R.color.black, null))
            setWebAlpha(100)

            xAxis.apply {
                valueFormatter = IndexAxisValueFormatter(categories)
                textSize = 10f
            }

            yAxis.apply {
                axisMinimum = 0f
                axisMaximum = 5f
                setDrawLabels(true)
                textSize = 8f
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