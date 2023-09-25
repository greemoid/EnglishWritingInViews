package com.example.englishwritinginviews.domain

import com.example.englishwritinginviews.data.MistakesRepository
import com.example.englishwritinginviews.data.entities.Mistake
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMistakesUseCase @Inject constructor(private val mistakesRepository: MistakesRepository) {
    suspend operator fun invoke(answer: String): Flow<Mistake> {
        if (answer.isEmpty()) {
            throw Exception("Please write an answer")
        }

        return mistakesRepository.fetchMistakes(answer)
    }
}