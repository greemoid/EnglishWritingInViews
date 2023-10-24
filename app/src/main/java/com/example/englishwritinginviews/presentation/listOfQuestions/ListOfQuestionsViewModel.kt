package com.example.englishwritinginviews.presentation.listOfQuestions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwritinginviews.R
import com.example.englishwritinginviews.domain.FetchQuestionsUseCase
import com.example.englishwritinginviews.domain.QuestionDomain
import com.example.englishwritinginviews.presentation.core.ConnectionObserver
import com.example.englishwritinginviews.presentation.core.ResourceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOfQuestionsViewModel @Inject constructor(
    private val useCase: FetchQuestionsUseCase,
    private val connectionObserver: ConnectionObserver,
    private val resourceManager: ResourceManager
) :
    ViewModel() {

    private val _questionsState: MutableStateFlow<List<QuestionDomain>> =
        MutableStateFlow(
            emptyList()
        )
    val questionsState: StateFlow<List<QuestionDomain>> = _questionsState

    private val _connectionState: MutableStateFlow<ConnectionUiState> = MutableStateFlow(
        ConnectionUiState("", false)
    )
    val connectionState: StateFlow<ConnectionUiState> = _connectionState

    init {
        getQuestions()
        getConnectionState()
    }

    fun getQuestions(filterList: Set<String> = emptySet()) {
        viewModelScope.launch {
            useCase(filterList).collect { list ->
                _questionsState.value = list
            }
        }
    }

    private fun getConnectionState() {
        viewModelScope.launch {
            connectionObserver.observe().collect { status ->
                when (status) {
                    ConnectionObserver.Status.Available -> _connectionState.value =
                        ConnectionUiState(resourceManager.getString(R.string.available), true)

                    ConnectionObserver.Status.Unavailable -> _connectionState.value =
                        ConnectionUiState(resourceManager.getString(R.string.unavailable), false)

                    ConnectionObserver.Status.Losing -> _connectionState.value =
                        ConnectionUiState(resourceManager.getString(R.string.losing), false)

                    ConnectionObserver.Status.Lost -> _connectionState.value =
                        ConnectionUiState(resourceManager.getString(R.string.lost), false)
                }
            }
        }
    }
}

data class ConnectionUiState(
    val message: String,
    val isConnected: Boolean
)