package com.example.englishwritinginviews.presentation.strike

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.children
import com.example.englishwritinginviews.R
import com.example.englishwritinginviews.databinding.CalendarDayLayoutBinding
import com.example.englishwritinginviews.databinding.FragmentStrikeBinding
import com.example.englishwritinginviews.presentation.core.BaseFragment
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.CalendarView
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

class StrikeFragment :
    BaseFragment<FragmentStrikeBinding>(FragmentStrikeBinding::inflate) {

    private val monthCalendarView: CalendarView get() = binding.calendarView

    private val selectedDates = mutableSetOf<LocalDate>()

    @RequiresApi(Build.VERSION_CODES.O)
    private val today = LocalDate.now()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun init() {

        val daysOfWeek = daysOfWeek()
        binding.legendLayout.root.children
            .map { it as TextView }
            .forEachIndexed { index, textView ->
                textView.text = daysOfWeek[index].displayText()
                textView.setTextColor(resources.getColor(R.color.red_button, context?.theme))
            }

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(100)
        val endMonth = currentMonth.plusMonths(100)
        setupMonthCalendar(startMonth, endMonth, currentMonth, daysOfWeek)
        /* binding.calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
             override fun bind(container: DayViewContainer, data: CalendarDay) {
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                     container.textView.text = data.date.dayOfMonth.toString()
                     if (data.date.dayOfMonth % 2 == 0) {
                         container.imageView.setImageDrawable(
                             ContextCompat.getDrawable(
                                 requireContext(),
                                 R.drawable.poo,
                             )
                         )
                     } else {
                         container.imageView.setImageDrawable(
                             ContextCompat.getDrawable(
                                 requireContext(),
                                 R.drawable.ok,
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
         setupMonthCalendar(startMonth, endMonth, currentMonth, firstDayOfWeek)
         binding.calendarView.scrollToMonth(currentMonth)
         binding.calendarView.outDateStyle = OutDateStyle.EndOfRow*/


    }

    private fun setupMonthCalendar(
        startMonth: YearMonth,
        endMonth: YearMonth,
        currentMonth: YearMonth,
        daysOfWeek: List<DayOfWeek>,
    ) {
        class DayViewContainer(view: View) : ViewContainer(view) {
            // Will be set when this container is bound. See the dayBinder.
            lateinit var day: CalendarDay
            val textView = CalendarDayLayoutBinding.bind(view).calendarDayText

            /*init {
                view.setOnClickListener {
                    if (day.position == DayPosition.MonthDate) {
                        dateClicked(date = day.date)
                    }
                }
            }*/
        }
        monthCalendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)

            @RequiresApi(Build.VERSION_CODES.O)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.day = data
                firstDayOfWeekFromLocale()
                bindDate(data.date, container.textView, data.position == DayPosition.MonthDate)
            }
        }
//        monthCalendarView.monthScrollListener = { updateTitle() }
        monthCalendarView.setup(startMonth, endMonth, daysOfWeek.first())
        monthCalendarView.scrollToMonth(currentMonth)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindDate(date: LocalDate, textView: TextView, isSelectable: Boolean) {
        textView.text = date.dayOfMonth.toString()
        if (isSelectable) {
            when {
                selectedDates.contains(date) -> {
                    textView.setTextColor(resources.getColor(R.color.red_button, context?.theme))
                }

                today == date -> {
                    textView.setTextColor(resources.getColor(R.color.white, context?.theme))
                }

                else -> {
                    textView.setTextColor(resources.getColor(R.color.white_text, context?.theme))
                }
            }
        } else {
            textView.setTextColor(resources.getColor(R.color.light_red_button, context?.theme))
            textView.background = null
        }
    }

    /*@SuppressLint("SetTextI18n")
    private fun updateTitle() {
        val month = monthCalendarView.findFirstVisibleMonth()?.yearMonth ?: return
        binding.exOneYearText.text = month.year.toString()
        binding.exOneMonthText.text = month.month.displayText(short = false)
    }*/
}

@RequiresApi(Build.VERSION_CODES.O)
fun YearMonth.displayText(short: Boolean = false): String {
    return "${this.month.displayText(short = short)} ${this.year}"
}

@RequiresApi(Build.VERSION_CODES.O)
fun Month.displayText(short: Boolean = true): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return getDisplayName(style, Locale.ENGLISH)
}

@RequiresApi(Build.VERSION_CODES.O)
fun DayOfWeek.displayText(uppercase: Boolean = false): String {
    return getDisplayName(TextStyle.SHORT, Locale.ENGLISH).let { value ->
        if (uppercase) value.uppercase(Locale.ENGLISH) else value
    }
}

class DayViewContainer(view: View) : ViewContainer(view) {

    val textView = CalendarDayLayoutBinding.bind(view).calendarDayText
//    val imageView = CalendarDayLayoutBinding.bind(view).imageView

}