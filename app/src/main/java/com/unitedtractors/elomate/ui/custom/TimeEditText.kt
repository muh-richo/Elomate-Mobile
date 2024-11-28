package com.unitedtractors.elomate.ui.custom

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.unitedtractors.elomate.R
import java.util.Calendar

class TimeEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs), View.OnClickListener {

    private val iconClock: Drawable =
        ContextCompat.getDrawable(context, R.drawable.ic_clock) ?: throw IllegalArgumentException("Drawable not found")

    init {
        maxLines = 1
        isFocusable = false
        setSingleLine(true)
        setOnClickListener(this)
        setupEditText()
    }

    private fun setupEditText() {
        setEditTextDrawables(startOfTheText = iconClock)
        hint = "HH:MM" // Placeholder teks untuk format waktu
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
        showTimePickerDialog()
    }

    @SuppressLint("DefaultLocale")
    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        // Tampilkan TimePickerDialog
        TimePickerDialog(context, { _, selectedHour, selectedMinute ->
            // Format waktu menjadi HH:MM
            val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            setText(formattedTime)
        }, hour, minute, true).show() // 'true' untuk format 24 jam
    }
}
