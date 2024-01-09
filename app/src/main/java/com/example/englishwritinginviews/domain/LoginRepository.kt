package com.example.englishwritinginviews.domain

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface LoginRepository {

    val currentUser: FirebaseUser?
    suspend fun signInViaGoogle(credential: AuthCredential): Flow<WorkResult<AuthResult>>

    suspend fun signInAnonymously(): Flow<WorkResult<AuthResult>>

    fun signOut()
}
