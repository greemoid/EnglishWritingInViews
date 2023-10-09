package com.example.englishwritinginviews.presentation.answer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwritinginviews.domain.FetchMistakesUseCase
import com.example.englishwritinginviews.domain.Mistake
import com.example.englishwritinginviews.domain.WorkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class AnswerUiState(
    val answer: String,
    val mistakes: List<Mistake>,
    val rating: Float,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)

@HiltViewModel
class AnswerViewModel @Inject constructor(
    private val fetchMistakesUseCase: FetchMistakesUseCase
) : ViewModel() {

    private val _uiState: MutableLiveData<AnswerUiState> = MutableLiveData()
    val uiState: LiveData<AnswerUiState> = _uiState


    fun fetchMistakeResponse(answer: String) = viewModelScope.launch {
        _uiState.value = AnswerUiState(
            isLoading = false,
            answer = "",
            mistakes = emptyList(),
            rating = 0f,
        )

        fetchMistakesUseCase(answer).collect { workResult ->
            when (workResult) {
                is WorkResult.Success -> {
                    val mistakes = workResult.data ?: emptyList()
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
