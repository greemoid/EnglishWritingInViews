package com.example.englishwritinginviews.presentation.strike

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.englishwritinginviews.R
import com.example.englishwritinginviews.databinding.CalendarDayLayoutBinding
import com.example.englishwritinginviews.databinding.FragmentStrikeBinding
import com.example.englishwritinginviews.presentation.account.AccountViewModel
import com.example.englishwritinginviews.presentation.core.BaseFragment
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.CalendarView
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

class StrikeFragment :
    BaseFragment<FragmentStrikeBinding>(FragmentStrikeBinding::inflate) {

    private val viewModel: AccountViewModel by activityViewModels()

    private val monthCalendarView: CalendarView get() = binding.calendarView
    private var selectedDates = mutableSetOf<LocalDate>()
    private val today = LocalDate.now()

    override fun init() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dates.collect { set ->
                    set.map {
                        selectedDates.add(it)
                        monthCalendarView.notifyCalendarChanged()
                    }
                }
            }
        }

        val daysOfWeek = daysOfWeek()
        binding.legendLayout.root.children
            .map { it as TextView }
            .forEachIndexed { index, textView ->
                textView.text = daysOfWeek[index].displayText()
                textView.setTextColor(resources.getColor(R.color.hint_color, context?.theme))
            }

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(100)
        val endMonth = currentMonth.plusMonths(100)
        setupMonthCalendar(startMonth, endMonth, currentMonth, daysOfWeek)
    }

    private fun setupMonthCalendar(
        startMonth: YearMonth,
        endMonth: YearMonth,
        currentMonth: YearMonth,
        daysOfWeek: List<DayOfWeek>,
    ) {
        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay
            val textView = CalendarDayLayoutBinding.bind(view).calendarDayText
        }

        monthCalendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)

            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.day = data
                firstDayOfWeekFromLocale()
                bindDate(data.date, container.textView, data.position == DayPosition.MonthDate)
            }
        }

        monthCalendarView.monthScrollListener = { updateTitle() }
        monthCalendarView.setup(startMonth, endMonth, daysOfWeek.first())
        monthCalendarView.scrollToMonth(currentMonth)
    }


    private fun bindDate(date: LocalDate, textView: TextView, isSelectable: Boolean) {
        textView.text = date.dayOfMonth.toString()
        if (isSelectable) {
            when {
                selectedDates.contains(date) -> {
                    textView.setBackgroundResource(R.drawable.green_textview_background)
                    if (today == date) {
                        textView.setTextColor(
                            resources.getColor(
                                R.color.light_red_button,
                                context?.theme
                            )
                        )
                    } else {
                        textView.setTextColor(resources.getColor(R.color.white, context?.theme))
                    }
                }

                today == date -> {
                    textView.setTextColor(resources.getColor(R.color.red_button, context?.theme))
                }

                else -> {
                    textView.setTextColor(resources.getColor(R.color.white_text, context?.theme))
                }
            }
        } else {
            textView.setTextColor(resources.getColor(R.color.hint_color, context?.theme))
            textView.background = null
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateTitle() {
        val month = monthCalendarView.findFirstVisibleMonth()?.yearMonth ?: return
        binding.tvYear.text = month.year.toString()
        binding.tvMonth.text = month.month.displayText(short = false)
    }
}

fun Month.displayText(short: Boolean = true): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return getDisplayName(style, Locale.ENGLISH)
}

fun DayOfWeek.displayText(uppercase: Boolean = false): String {
    return getDisplayName(TextStyle.SHORT, Locale.ENGLISH).let { value ->
        if (uppercase) value.uppercase(Locale.ENGLISH) else value
    }
}
