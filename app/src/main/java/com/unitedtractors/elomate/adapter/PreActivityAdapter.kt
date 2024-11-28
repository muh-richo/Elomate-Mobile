package com.unitedtractors.elomate.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.data.network.response.PreActivityResponse
import com.unitedtractors.elomate.databinding.CardAssignmentBinding

class PreActivityAdapter(
    private val preActivityList: List<PreActivityResponse>,
    private val onPreActivityClick: (Int) -> Unit
) : RecyclerView.Adapter<PreActivityAdapter.PreActivityViewHolder>() {

    inner class PreActivityViewHolder(private val binding: CardAssignmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(preActivity: PreActivityResponse) {
            binding.tvTitleAssignment.text = preActivity.title
            binding.tvCourseName.text = preActivity.namaCourse
            binding.tvDeadline.text = preActivity.tanggalSelesai
            binding.tvStatus.text = preActivity.active

            if (preActivity.active == "Complete") {
                binding.ivCalendar.setColorFilter(
                    ContextCompat.getColor(binding.root.context, R.color.neutral_500)
                )
                binding.tvDeadline.setTextColor(
                    ContextCompat.getColor(binding.root.context, R.color.neutral_500)
                )
                binding.tvStatus.setTextColor(
                    ContextCompat.getColor(binding.root.context, R.color.success_700)
                )
            }

            // Set click listener
            binding.root.setOnClickListener {
                preActivity.assignmentId?.let { it1 -> onPreActivityClick(it1) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreActivityViewHolder {
        val binding = CardAssignmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PreActivityViewHolder(binding)
    }

    override fun getItemCount(): Int = preActivityList.size

    override fun onBindViewHolder(holder: PreActivityViewHolder, position: Int) {
        holder.bind(preActivityList[position])
    }

}