package com.example.englishwritinginviews.domain

import com.example.englishwritinginviews.data.WorkResult
import com.example.englishwritinginviews.data.entities.MistakeApiModel
import kotlinx.coroutines.flow.Flow

interface MistakesRepository {
    suspend fun fetchMistakes(answer: String): Flow<WorkResult<MistakeApiModel>>

}