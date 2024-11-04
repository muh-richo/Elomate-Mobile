package com.unitedtractors.elomate.ui.home.schedule

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.view.ViewContainer
import com.unitedtractors.elomate.R

class DayViewContainer(view: View) : ViewContainer(view) {
    val textView = view.findViewById<TextView>(R.id.calendarDayText)
//    val dayNameTextView: TextView = view.findViewById(R.id.calendarDayName)

    val itemView: View = view // Ini akan menjadi reference untuk item view.

}
