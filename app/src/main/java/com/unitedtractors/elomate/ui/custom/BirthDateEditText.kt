package com.unitedtractors.elomate.ui.custom

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.unitedtractors.elomate.R
import java.util.Calendar

class BirthDateEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs), View.OnClickListener {

    private var iconCalendar: Drawable = ContextCompat.getDrawable(context, R.drawable.ic_calender_time) as Drawable

    init {
        maxLines = 1
        isFocusable = false // Tidak memungkinkan pengguna mengetik langsung
        setSingleLine(true)
        setOnClickListener(this) // Menambahkan listener untuk membuka date picker
        setupEditText()
    }

    private fun setupEditText() {
        setEditTextDrawables(startOfTheText = iconCalendar)
        hint = "Pilih Tanggal Lahir" // Placeholder teks
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    private fun setEditTextDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText, topOfTheText, endOfTheText, bottomOfTheText
        )
        compoundDrawablePadding = 20
    }

    override fun onClick(v: View?) {
        showDatePickerDialog()
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
            // Menampilkan hasil pemilihan tanggal ke EditText
            val formattedDate = String.format("%02d-%02d-%04d", selectedDay, selectedMonth + 1, selectedYear)
            setText(formattedDate)
        }, year, month, day).show()
    }
}