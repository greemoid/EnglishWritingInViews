package com.example.englishwritinginviews.presentation.strike

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.englishwritinginviews.presentation.account.AccountViewModel
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.DayOfWeek
import java.time.YearMonth
import java.util.Locale

class StrikeFragment : Fragment() {

    private val viewModel: AccountViewModel by activityViewModels()


    // todo to try to downgrade calendar version

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                val currentMonth = remember { YearMonth.now() }
                val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
                val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed
                val firstDayOfWeek =
                    remember { firstDayOfWeekFromLocale() } // Available from the library

                val state = rememberCalendarState(
                    startMonth = startMonth,
                    endMonth = endMonth,
                    firstVisibleMonth = currentMonth,
                    firstDayOfWeek = firstDayOfWeek
                )


                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    HorizontalCalendar(
                        state = state,
                        dayContent = { Day(it) },
                        monthHeader = { month ->
                            // You may want to use `remember {}` here so the mapping is not done
                            // every time as the days of week order will never change unless
                            // you set a new value for `firstDayOfWeek` in the state.
                            val daysOfWeek = month.weekDays.first().map { it.date.dayOfWeek }
                            DaysOfWeekTitle(daysOfWeek = daysOfWeek)
                        }
                    )
                }


            }
        }
    }

    @Composable
    fun Day(day: CalendarDay) {
        Box(
            modifier = Modifier
                .aspectRatio(1f), // This is important for square sizing!
            contentAlignment = Alignment.Center
        ) {
            Text(text = day.date.dayOfMonth.toString())
        }
    }

    @Composable
    fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
        Row(modifier = Modifier.fillMaxWidth()) {
            for (dayOfWeek in daysOfWeek) {
                Text(
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    text = dayOfWeek.getDisplayName(
                        java.time.format.TextStyle.SHORT,
                        Locale.getDefault()
                    ),
                )
            }
        }
    }


    /* private val monthCalendarView: CalendarView get() = binding.calendarView
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
     }*/
}
