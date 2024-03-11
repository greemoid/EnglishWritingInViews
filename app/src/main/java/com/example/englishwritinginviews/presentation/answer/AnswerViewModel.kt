package com.example.englishwritinginviews.presentation.answer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwritinginviews.domain.FetchAIResponseUseCase
import com.example.englishwritinginviews.domain.FetchMistakesUseCase
import com.example.englishwritinginviews.domain.LivesHandler
import com.example.englishwritinginviews.domain.Mistake
import com.example.englishwritinginviews.domain.QuestionDomain
import com.example.englishwritinginviews.domain.UpdateAnswerUseCase
import com.example.englishwritinginviews.domain.WorkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class AnswerUiState(
    val answer: String,
    val mistakes: List<Mistake>,
    val rating: Float,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)

data class AIUiState(
    val response: String,
    val isLoading: Boolean,
    val isError: Boolean
)

@HiltViewModel
class AnswerViewModel @Inject constructor(
    private val fetchMistakesUseCase: FetchMistakesUseCase,
    private val updateAnswerUseCase: UpdateAnswerUseCase,
    private val fetchAIResponseUseCase: FetchAIResponseUseCase,
    private val livesHandler: LivesHandler
) : ViewModel() {

    private val _uiState: MutableLiveData<AnswerUiState> = MutableLiveData()
    val uiState: LiveData<AnswerUiState> = _uiState

    private val _aiState: MutableLiveData<AIUiState> = MutableLiveData()
    val aiState: LiveData<AIUiState> = _aiState

    private var mistakes = emptyList<Mistake>()


    private var _stateFlow: MutableStateFlow<QuestionDomain> =
        MutableStateFlow(
            QuestionDomain(
                0,
                "question",
                "answer",
                "difficulty",
                "color",
                false,
                1,
                0.0
            )
        )
    val stateFlow: StateFlow<QuestionDomain> = _stateFlow

    fun update(id: Int, answer: String, rating: Float) {
        viewModelScope.launch {
            val currentDate = System.currentTimeMillis()
            _stateFlow.value = updateAnswerUseCase(id, answer, currentDate, rating)
        }
    }

    fun fetchAIResponse(answer: String) {
        val prompt =
            "I wrote a text and get mistakes from my teacher. So, I want to get detailed explanation for this mistakes from you" + mistakes.toString() + answer
        viewModelScope.launch {
            _aiState.value = AIUiState(
                response = "",
                isLoading = false,
                isError = false
            )
            fetchAIResponseUseCase(prompt).collect { workResult ->
                when (workResult) {
                    is WorkResult.Success -> {
                        _aiState.value = AIUiState(
                            response = workResult.data ?: "",
                            isError = false,
                            isLoading = false
                        )
                    }

                    is WorkResult.Error -> {
                        _aiState.value = AIUiState(
                            response = workResult.message ?: "",
                            isError = true,
                            isLoading = false
                        )
                    }

                    is WorkResult.Loading -> {
                        _aiState.value = AIUiState(
                            response = "",
                            isError = false,
                            isLoading = true
                        )
                    }
                }
            }
        }
    }


    fun fetchMistakeResponse(answer: String) = viewModelScope.launch {
        _uiState.value = AnswerUiState(
            isLoading = false,
            answer = "",
            mistakes = emptyList(),
            rating = 0f,
        )

        livesHandler.useLife()

        fetchMistakesUseCase(answer).collect { workResult ->
            when (workResult) {
                is WorkResult.Success -> {
                    mistakes = workResult.data ?: emptyList()
                    val rating = calculateRating(mistakes)

                    _uiState.value = AnswerUiState(
                        answer = answer,
                        mistakes = mistakes,
                        rating = rating,
                        isLoading = false
                    )
                }

                is WorkResult.Error -> {
                    _uiState.value = AnswerUiState(
                        answer = workResult.message!!,
                        mistakes = emptyList(),
                        rating = 0f,
                        isLoading = false,
                        isError = true
                    )
                }

                is WorkResult.Loading -> {
                    _uiState.value = AnswerUiState(
                        answer = "",
                        mistakes = emptyList(),
                        rating = 0f,
                        isLoading = true
                    )
                }
            }
        }
    }

    private fun calculateRating(mistakes: List<Mistake>): Float {
        //todo add rating to model
        return when (mistakes.size) {
            in 0..1 -> 5f
            in 2..3 -> 4.5f
            in 4..5 -> 4f
            in 6..7 -> 3.5f
            in 8..9 -> 3f
            in 10..11 -> 2.5f
            in 12..13 -> 2f
            in 14..15 -> 1.5f
            in 16..17 -> 1f
            else -> 0.5f
        }
    }


}
