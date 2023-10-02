package com.example.englishwritinginviews.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMistakesUseCase @Inject constructor(private val mistakesRepository: MistakesRepository) {
    suspend operator fun invoke(answer: String): Flow<WorkResult<List<Mistake>>> =
        mistakesRepository.fetchMistakes(answer)

}


