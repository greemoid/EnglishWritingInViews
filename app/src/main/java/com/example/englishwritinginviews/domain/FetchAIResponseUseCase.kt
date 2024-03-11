package com.example.englishwritinginviews.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAIResponseUseCase @Inject constructor(private val repository: MistakesRepository) {
    suspend operator fun invoke(prompt: String): Flow<WorkResult<String>> = repository.fetchAIResponse(prompt)

}