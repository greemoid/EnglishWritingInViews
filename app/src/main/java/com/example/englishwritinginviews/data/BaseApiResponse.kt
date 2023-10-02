package com.example.englishwritinginviews.data

import com.example.englishwritinginviews.domain.WorkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response


abstract class BaseApiResponse {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Flow<WorkResult<T>> = flow {
        try {
            emit(WorkResult.Loading())
            val response = apiCall()
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()
                body?.let {
                    emit(WorkResult.Success(body))
                } ?: run {
                    emit(error("${response.code()} ${response.message()}"))
                }
            } else {
                emit(error("${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(error(e.message ?: e.toString()))
        }
    }

    private fun <T> error(errorMessage: String): WorkResult.Error<T> =
        WorkResult.Error("Api call failed $errorMessage")
}







