package com.unitedtractors.elomate.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.data.network.response.AssignmentResponse
import com.unitedtractors.elomate.databinding.CardTaskBinding

class ToDoAdapter(
    private var todoList: List<AssignmentResponse>,
    private val onTodoClick: (Int) -> Unit
) : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

    inner class ToDoViewHolder(private val binding: CardTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(assignment: AssignmentResponse) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding = CardTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ToDoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bind(todoList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newToDoList: List<AssignmentResponse>) {
        todoList = newToDoList
        notifyDataSetChanged()
    }
}