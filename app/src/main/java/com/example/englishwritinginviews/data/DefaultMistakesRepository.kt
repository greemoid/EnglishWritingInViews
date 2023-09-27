package com.example.englishwritinginviews.data

import com.example.englishwritinginviews.data.entities.Mistake
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DefaultMistakesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : MistakesRepository, BaseApiResponse() {
    override suspend fun fetchMistakes(answer: String): Flow<WorkResult<Mistake>> {
        return flow<WorkResult<Mistake>> {
            emit(safeApiCall { remoteDataSource.fetchMistakes(answer) })
        }.flowOn(Dispatchers.IO)
    }
}

