package com.example.englishwritinginviews.presentation.account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwritinginviews.domain.FetchAnsweredQuestionsUseCase
import com.example.englishwritinginviews.domain.QuestionDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val fetchAnsweredQuestionsUseCase: FetchAnsweredQuestionsUseCase,
) : ViewModel() {

    private val _questions = MutableStateFlow<List<QuestionDomain>>(emptyList())
    val questions: StateFlow<List<QuestionDomain>> = _questions

    private val _dates = MutableStateFlow<Set<LocalDate>>(emptySet())
    val dates: StateFlow<Set<LocalDate>> = _dates

    private val _numberOfDays = MutableStateFlow(0)
    val numberOfDays: StateFlow<Int> = _numberOfDays

    init {
        fetchQuestions()
    }

    private fun fetchQuestions() {
        // there were an exception with coroutine cancellation
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
            Log.d("asac", "CoroutineExceptionHandler got $exception in $coroutineContext")
        }
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO + exceptionHandler)
        scope.launch {
            fetchAnsweredQuestionsUseCase().collect { list ->
                _questions.value = list.asReversed()
                _dates.value = getSetOfDates(list)
            }
        }
        /*viewModelScope.launch {
            fetchAnsweredQuestionsUseCase().collect { list ->
                _questions.value = list.asReversed()
                _dates.value = getSetOfDates(list)
            }
        }*/
    }

    private fun getSetOfDates(list: List<QuestionDomain>): Set<LocalDate> {
        val mutableSetDates = mutableSetOf<LocalDate>()
        for (model in list) {
            mutableSetDates.add(toLocalDate(model.answeredAt))
        }
        _numberOfDays.value = mutableSetDates.size
        return mutableSetDates
    }


    private fun toLocalDate(date: Long): LocalDate {
        val instant: Instant = Instant.ofEpochMilli(date)
        val zoneId: ZoneId = ZoneId.of("UTC")

        val year = instant.atZone(zoneId).year
        val month = instant.atZone(zoneId).monthValue
        val day = instant.atZone(zoneId).dayOfMonth

        return LocalDate.of(year, month, day)
    }
}
