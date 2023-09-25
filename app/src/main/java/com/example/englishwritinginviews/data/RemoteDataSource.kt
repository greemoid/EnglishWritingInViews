package com.example.englishwritinginviews.data

import com.example.englishwritinginviews.data.entities.Mistake
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun fetchMistakes(answer: String): Flow<WorkResult<Mistake>>
}