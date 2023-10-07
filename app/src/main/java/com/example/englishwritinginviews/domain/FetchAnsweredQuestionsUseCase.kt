package com.example.englishwritinginviews.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAnsweredQuestionsUseCase @Inject constructor(private val repository: MistakesRepository) {
    operator fun invoke(): Flow<List<QuestionDomain>> = repository.getAnsweredQuestions()
}