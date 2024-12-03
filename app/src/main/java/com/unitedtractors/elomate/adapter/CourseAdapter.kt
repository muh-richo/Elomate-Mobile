package com.unitedtractors.elomate.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.data.network.response.CourseResponse
import com.unitedtractors.elomate.databinding.CardCourseBinding

class CourseAdapter(
    private val courses: List<CourseResponse>,
    private val onCourseClick: (Int) -> Unit
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    inner class CourseViewHolder(private val binding: CardCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(course: CourseResponse) {
            binding.tvCourseName.text = course.namaCourse
            binding.tvMentor.text = "Mentor: ${course.fasilitatorName}"
            val progressValue = course.progress ?: 0
            binding.circleProgressbar.progress = progressValue
            binding.progressText.text = "$progressValue%"

            // Set click listener
            binding.root.setOnClickListener {
                course.courseId?.let { it1 -> onCourseClick(it1) } // Kirim courseId ke lambda onCourseClick
            }
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