package com.unitedtractors.elomate.ui.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.R

data class Task(val deadline: String, val name: String)

class TaskAdapter(private val taskList: List<Task>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNameTask: TextView = view.findViewById(R.id.tv_name_task)
        val tvDeadline: TextView = view.findViewById(R.id.tv_deadline)
        val tvStatus: TextView = view.findViewById(R.id.tv_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.tvNameTask.text = task.name
        holder.tvDeadline.text = task.deadline
        holder.tvStatus.text = "Incomplete" // Contoh status, bisa disesuaikan
    }

    override fun getItemCount(): Int = taskList.size
}
