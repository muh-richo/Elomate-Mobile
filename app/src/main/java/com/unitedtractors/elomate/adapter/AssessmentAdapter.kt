package com.unitedtractors.elomate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.data.network.response.AssessmentsItem
import com.unitedtractors.elomate.databinding.CardAssessmentBinding

class AssessmentAdapter(
    private val assessments: List<AssessmentsItem>,
    private val onAssessmentClick: (Int) -> Unit
) : RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder>()  {

    inner class AssessmentViewHolder(private val binding: CardAssessmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(assessment: AssessmentsItem) {
            binding.tvTitleAssessment.text = assessment.title
            binding.tvDescAssessment.text = assessment.description
            binding.tvDeadline.text = assessment.tanggalSelesai

            binding.root.setOnClickListener {
                assessment.assessmentId?.let{ it1 -> onAssessmentClick(it1) }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssessmentViewHolder {
        val binding = CardAssessmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AssessmentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return assessments.size
    }

    override fun onBindViewHolder(holder: AssessmentViewHolder, position: Int) {
        holder.bind(assessments[position])
    }
}