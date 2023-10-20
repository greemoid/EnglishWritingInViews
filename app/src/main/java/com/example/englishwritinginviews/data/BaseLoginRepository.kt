package com.example.englishwritinginviews.data

import com.example.englishwritinginviews.domain.LoginRepository
import com.example.englishwritinginviews.domain.WorkResult
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class BaseLoginRepository(
    private val auth: FirebaseAuth
) : LoginRepository {
    override val currentUser: FirebaseUser?
        get() = auth.currentUser


    override suspend fun signInViaGoogle(credential: AuthCredential): Flow<WorkResult<AuthResult>> {
        return flow {
            emit(WorkResult.Loading())
            val result = auth.signInWithCredential(credential).await()
            emit(WorkResult.Success(result))
        }.catch { emit(WorkResult.Error(it.message.toString())) }
    }

    override suspend fun signInAnonymously(): Flow<WorkResult<AuthResult>> {
        return flow {
            emit(WorkResult.Loading())
            val result = auth.signInAnonymously().await()
            emit(WorkResult.Success(result))
        }.catch { emit(WorkResult.Error(it.message.toString())) }
    }

    override fun signOut() = auth.signOut()
}