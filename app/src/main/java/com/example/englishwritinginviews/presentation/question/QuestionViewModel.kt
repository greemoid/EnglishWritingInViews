package com.example.englishwritinginviews.presentation.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwritinginviews.domain.QuestionDomain
import com.example.englishwritinginviews.domain.UpdateAnswerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val updateAnswerUseCase: UpdateAnswerUseCase) :
    ViewModel() {

    private var _stateFlow: MutableStateFlow<QuestionDomain> =
        MutableStateFlow(QuestionDomain(0, "question", "answer", "difficulty", "color", false, 1))
    val stateFlow: StateFlow<QuestionDomain> = _stateFlow

    fun update(id: Int, answer: String) {
        viewModelScope.launch {
            val currentDate = System.currentTimeMillis()
            _stateFlow.value = updateAnswerUseCase(id, answer, currentDate)
        }
    }

}