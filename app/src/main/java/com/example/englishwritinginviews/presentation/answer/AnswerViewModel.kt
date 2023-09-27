package com.example.englishwritinginviews.presentation.answer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwritinginviews.data.WorkResult
import com.example.englishwritinginviews.data.entities.Mistake
import com.example.englishwritinginviews.domain.FetchMistakesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AnswerViewModel @Inject constructor(
    private val useCase: FetchMistakesUseCase
) : ViewModel() {

    private val _response: MutableLiveData<WorkResult<Mistake>> = MutableLiveData()
    val response: LiveData<WorkResult<Mistake>> = _response


    fun fetchMistakeResponse(answer: String) = viewModelScope.launch {
        useCase(answer).collect() { values ->
            _response.value = values
        }
    }


}