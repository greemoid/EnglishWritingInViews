package com.example.englishwritinginviews.domain

import kotlinx.coroutines.flow.Flow


class FetchQuestionsUseCase(
    private val repository: MistakesRepository
) {
    operator fun invoke(): Flow<List<QuestionDomain>> = repository.getAllQuestions()
}