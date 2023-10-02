package com.example.englishwritinginviews.domain

import kotlinx.coroutines.flow.Flow

interface MistakesRepository {
    suspend fun fetchMistakes(answer: String): Flow<WorkResult<List<Mistake>>>

}