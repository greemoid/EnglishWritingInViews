package com.example.englishwritinginviews.presentation.listOfQuestions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwritinginviews.domain.FetchQuestionsUseCase
import com.example.englishwritinginviews.domain.QuestionDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
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

    fun getQuestions(filterList: Set<String> = emptySet<String>()) {
        viewModelScope.launch {
            useCase(filterList).collect { list ->
                _questionsState.value = list
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("asac", "l canceled")
        viewModelScope.cancel()
    }
}