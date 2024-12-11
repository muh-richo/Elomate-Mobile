package com.unitedtractors.elomate.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.data.network.response.CourseResponse
import com.unitedtractors.elomate.databinding.ItemCourseProgressBinding

class CourseProgressAdapter(
    private val courses: List<CourseResponse>,
    private val onCourseClick: (Int) -> Unit
) : RecyclerView.Adapter<CourseProgressAdapter.CourseProgressViewHolder>() {

    inner class CourseProgressViewHolder(private val binding: ItemCourseProgressBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(course: CourseResponse) {
            binding.tvTitleCourse.text = course.namaCourse
            val progressValue = course.progress ?: 0
            binding.progressBarCourse.progress = progressValue
            binding.tvValueProgress.text = "$progressValue%"

            // Set click listener
            binding.root.setOnClickListener {
                course.courseId?.let { it1 -> onCourseClick(it1) } // Kirim courseId ke lambda onCourseClick
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseProgressViewHolder {
        val binding = ItemCourseProgressBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CourseProgressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseProgressViewHolder, position: Int) {
        holder.bind(courses[position])
    }

    override fun getItemCount(): Int = courses.size
}