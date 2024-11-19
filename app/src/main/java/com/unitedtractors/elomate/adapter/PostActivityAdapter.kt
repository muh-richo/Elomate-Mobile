package com.unitedtractors.elomate.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.data.network.response.AssignmentResponse
import com.unitedtractors.elomate.data.network.response.ListActivityItem
import com.unitedtractors.elomate.databinding.CardAssignmentBinding

class PostActivityAdapter(
    private val postActivityList: List<ListActivityItem>,
    private val onPostActivityClick: (Int) -> Unit
) : RecyclerView.Adapter<PostActivityAdapter.PostActivityViewHolder>() {

    inner class PostActivityViewHolder(private val binding: CardAssignmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(assignment: ListActivityItem) {
            binding.tvTitleAssignment.text = assignment.title
            binding.tvCourseName.text = assignment.namaCourse
            binding.tvDeadline.text = assignment.tanggalSelesai
            binding.tvStatus.text = assignment.active

            // Set click listener
            binding.root.setOnClickListener {
                assignment.assignmentId?.let { id -> onPostActivityClick(id) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostActivityViewHolder {
        val binding = CardAssignmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PostActivityViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postActivityList.size
    }

    override fun onBindViewHolder(holder: PostActivityViewHolder, position: Int) {
        holder.bind(postActivityList[position])
    }
}