package com.unitedtractors.elomate.ui.home.ToDo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.R

data class Task(
    val course: String,
    val name: String,
    val deadline: String,
    val status: String
)

class ToDoAdapter(private var tasks: List<Task>) : RecyclerView.Adapter<ToDoAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCourse: TextView = view.findViewById(R.id.tv_course)
        val tvNameTask: TextView = view.findViewById(R.id.tv_name_task)
        val tvDeadline: TextView = view.findViewById(R.id.tv_deadline)
        val tvStatus: TextView = view.findViewById(R.id.tv_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.tvCourse.text = task.course
        holder.tvNameTask.text = task.name
        holder.tvDeadline.text = task.deadline
        holder.tvStatus.text = task.status
    }

    override fun getItemCount(): Int = tasks.size

    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}
