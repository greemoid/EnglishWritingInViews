package com.example.englishwritinginviews.presentation

import androidx.lifecycle.ViewModel
import com.example.englishwritinginviews.domain.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {
    fun signOut() = repository.signOut()
}