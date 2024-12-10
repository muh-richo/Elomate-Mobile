package com.unitedtractors.elomate.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.data.network.response.ListActivityItem
import com.unitedtractors.elomate.databinding.CardTaskBinding

class ScheduleAdapter(
    private var todoList: List<ListActivityItem>,
    private val onTodoClick: (Int) -> Unit
) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    inner class ScheduleViewHolder(private val binding: CardTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(assignment: ListActivityItem) {
            binding.tvTitleTask.text = assignment.title
            binding.tvCourseName.text = assignment.namaCourse
            binding.tvDeadline.text = assignment.tanggalSelesai
            binding.tvStatus.text = assignment.active

            // Set click listener
            binding.root.setOnClickListener {
                assignment.assignmentId?.let { it1 -> onTodoClick(it1) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = CardTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ScheduleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(todoList[position])
    }
}