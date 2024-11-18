package com.unitedtractors.elomate.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.data.network.response.PreActivityResponse
import com.unitedtractors.elomate.databinding.CardAssignmentBinding

class PreActivityAdapter(
    private val preActivityList: List<PreActivityResponse>,
    private val onPreActivityClick: (Int) -> Unit
) : RecyclerView.Adapter<PreActivityAdapter.PreActivityViewHolder>()  {

    inner class PreActivityViewHolder(private val binding: CardAssignmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(assignment: PreActivityResponse) {
            binding.tvTitleAssignment.text = assignment.title
            binding.tvCourseName.text = assignment.namaCourse
            binding.tvDeadline.text = assignment.tanggalSelesai
            binding.tvStatus.text = assignment.active

            // Set click listener
            binding.root.setOnClickListener {
                assignment.assignmentId?.let { it1 -> onPreActivityClick(it1) }
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