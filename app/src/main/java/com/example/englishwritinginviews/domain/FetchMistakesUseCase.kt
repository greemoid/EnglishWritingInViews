package com.example.englishwritinginviews.domain

import com.example.englishwritinginviews.data.WorkResult
import com.example.englishwritinginviews.data.entities.MistakeApiModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMistakesUseCase @Inject constructor(private val mistakesRepository: MistakesRepository) {
    suspend operator fun invoke(answer: String): Flow<WorkResult<MistakeApiModel>> =
        mistakesRepository.fetchMistakes(answer)

}