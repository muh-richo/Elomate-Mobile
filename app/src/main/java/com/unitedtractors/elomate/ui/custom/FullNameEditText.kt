package com.unitedtractors.elomate.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.unitedtractors.elomate.R

class FullNameEditText  @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {
    private var iconFullName: Drawable = ContextCompat.getDrawable(context, R.drawable.ic_user_edit) as Drawable

    init {
        maxLines = 1
        setSingleLine(true)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setEditTextDrawables(startOfTheText = iconFullName)
    }

    private fun setEditTextDrawables(startOfTheText: Drawable? = null, topOfTheText:Drawable? = null, endOfTheText:Drawable? = null, bottomOfTheText: Drawable? = null){
        setCompoundDrawablesWithIntrinsicBounds(startOfTheText, topOfTheText, endOfTheText, bottomOfTheText)
        compoundDrawablePadding = 20
    }
}