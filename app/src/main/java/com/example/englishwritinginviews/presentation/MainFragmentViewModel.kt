package com.example.englishwritinginviews.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwritinginviews.domain.LoginRepository
import com.example.englishwritinginviews.domain.WorkResult
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {


    val currentUser: FirebaseUser?
        get() = repository.currentUser

    private val _googleState = MutableStateFlow(GoogleSignInState())
    val googleState: StateFlow<GoogleSignInState> = _googleState

    private val _signInState = MutableStateFlow(SignInState())
    val signInState: StateFlow<SignInState> = _signInState


    fun signInAnonymously() = viewModelScope.launch {
        repository.signInAnonymously().collect { result ->
            when (result) {
                is WorkResult.Error -> _signInState.value =
                    SignInState(isError = result.message.toString())

                is WorkResult.Success -> _signInState.value =
                    SignInState(isSuccess = "Sign In success")

                is WorkResult.Loading -> _signInState.value = SignInState(isLoading = true)
            }
        }
    }

    fun signInGoogle(credential: AuthCredential) = viewModelScope.launch {
        repository.signInViaGoogle(credential).collect { result ->
            when (result) {
                is WorkResult.Loading -> GoogleSignInState(loading = true)
                is WorkResult.Error -> GoogleSignInState(error = result.message.toString())
                is WorkResult.Success -> GoogleSignInState(success = result.data)
            }
        }
    }

}

data class GoogleSignInState(
    val success: AuthResult? = null,
    val loading: Boolean = false,
    val error: String? = ""
)

data class SignInState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String = ""
)