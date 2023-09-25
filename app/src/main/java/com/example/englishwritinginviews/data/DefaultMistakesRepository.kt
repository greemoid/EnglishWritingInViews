package com.example.englishwritinginviews.data

import com.example.englishwritinginviews.data.entities.Mistake
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultMistakesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : RemoteDataSource{
    override suspend fun fetchMistakes(answer: String): Flow<WorkResult<Mistake>> {
        return remoteDataSource.fetchMistakes(answer)
    }
}