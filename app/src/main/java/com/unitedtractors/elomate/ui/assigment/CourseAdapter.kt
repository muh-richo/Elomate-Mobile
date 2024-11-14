package com.unitedtractors.elomate.ui.assigment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.data.network.response.CourseResponseItem


data class Course(
    val nameCourse: String,
    val mentor: String
)

class CourseAdapter(private val courseList: List<Course>) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    inner class CourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCourse: TextView = view.findViewById(R.id.tv_course_name)
        val tvMentor: TextView = view.findViewById(R.id.tv_mentor)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseAdapter.CourseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.course_item, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val courses = courseList[position]
        holder.tvCourse.text = courses.nameCourse
        holder.tvMentor.text = courses.mentor
    }

    override fun getItemCount(): Int = courseList.size

}