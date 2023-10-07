package com.example.englishwritinginviews.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwritinginviews.domain.FetchAnsweredQuestionsUseCase
import com.example.englishwritinginviews.domain.QuestionDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val fetchAnsweredQuestionsUseCase: FetchAnsweredQuestionsUseCase
) : ViewModel() {

    private val _questions = MutableStateFlow<List<QuestionDomain>>(emptyList())
    val questions: StateFlow<List<QuestionDomain>> = _questions

    init {
        fetchQuestions()
    }

    private fun fetchQuestions() = viewModelScope.launch {
        fetchAnsweredQuestionsUseCase().collect { list ->
            _questions.value = list
        }
    }
}