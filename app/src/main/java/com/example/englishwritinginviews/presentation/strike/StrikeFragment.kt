package com.example.englishwritinginviews.presentation.strike

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.example.englishwritinginviews.R
import com.example.englishwritinginviews.databinding.CalendarDayLayoutBinding
import com.example.englishwritinginviews.databinding.FragmentStrikeBinding
import com.example.englishwritinginviews.presentation.core.BaseFragment
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.YearMonth

class StrikeFragment :
    BaseFragment<FragmentStrikeBinding>(FragmentStrikeBinding::inflate) {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun init() {
        binding.calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    container.textView.text = data.date.dayOfMonth.toString()
                    if (data.date.dayOfMonth % 2 == 0) {
                        container.imageView.setImageDrawable(
                            resources?.getDrawable(
                                R.drawable.ic_checked,
                                context!!.theme
                            )
                        )
                    } else {
                        container.imageView.setImageDrawable(
                            resources?.getDrawable(
                                R.drawable.ic_unchecked,
                                context!!.theme
                            )
                        )
                    }
                } else {
                    container.textView.text = "1"
                }
            }

            override fun create(view: View): DayViewContainer = DayViewContainer(view)

        }

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(100)  // Adjust as needed
        val endMonth = currentMonth.plusMonths(100)  // Adjust as needed
        val firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
        binding.calendarView.setup(startMonth, endMonth, firstDayOfWeek)
        binding.calendarView.scrollToMonth(currentMonth)

    }

}

class DayViewContainer(view: View) : ViewContainer(view) {

    val textView = CalendarDayLayoutBinding.bind(view).calendarDayText
    val imageView = CalendarDayLayoutBinding.bind(view).imageView

}