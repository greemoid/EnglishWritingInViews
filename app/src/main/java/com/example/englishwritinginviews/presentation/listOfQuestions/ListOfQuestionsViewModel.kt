package com.example.englishwritinginviews.presentation.listOfQuestions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwritinginviews.domain.FetchQuestionsUseCase
import com.example.englishwritinginviews.domain.QuestionDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOfQuestionsViewModel @Inject constructor(private val useCase: FetchQuestionsUseCase) :
    ViewModel() {

    private val _questionsState: MutableStateFlow<List<QuestionDomain>> =
        MutableStateFlow(
            emptyList()
        )
    val questionsState: StateFlow<List<QuestionDomain>> = _questionsState

    init {
        getQuestions()
    }
    fun getQuestions(filterList: List<String> = emptyList()) {
        viewModelScope.launch {
            useCase(filterList).collect { list ->
                _questionsState.value = list
            }
        }
    }
}