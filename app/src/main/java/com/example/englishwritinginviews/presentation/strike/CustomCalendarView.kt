package com.example.englishwritinginviews.presentation.strike

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.CalendarView

class CustomCalendarView: CalendarView {

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context!!, attrs, defStyleAttr, defStyleRes)


    private val dayColors = mutableMapOf<Int, Int>()

    fun setDayColor(date: Int, color: Int) {
        dayColors[date] = color
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (date in 1..31) {
            val color = dayColors[date] ?: Color.BLACK
            canvas.drawCircle(date.toFloat() * 10, date.toFloat() * 10, 5f, Paint(color))
        }
    }
}