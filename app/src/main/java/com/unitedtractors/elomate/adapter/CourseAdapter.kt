package com.unitedtractors.elomate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.data.network.response.CourseResponseItem
import com.unitedtractors.elomate.databinding.CardCourseBinding

class CourseAdapter(private val courses: List<CourseResponseItem>) :
    RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    inner class CourseViewHolder(private val binding: CardCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(course: CourseResponseItem) {
            binding.tvCourseName.text = course.namaCourse
            binding.tvMentor.text = "Mentor: ${course.fasilitatorName}"

            // Get progress value from the API data
            val progressValue = course.progress ?: 0

            // Set ProgressBar progress and TextView text with the progress value
            binding.circleProgressbar.progress = progressValue
            binding.progressText.text = "$progressValue%"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = CardCourseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(courses[position])
    }

    override fun getItemCount(): Int = courses.size
}