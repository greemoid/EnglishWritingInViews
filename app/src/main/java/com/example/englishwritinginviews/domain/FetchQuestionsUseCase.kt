package com.example.englishwritinginviews.domain

import kotlinx.coroutines.flow.Flow


class FetchQuestionsUseCase(
    private val repository: MistakesRepository
) {
    operator fun invoke(filterList: Set<String>): Flow<List<QuestionDomain>> =
        repository.getQuestions(filterList)
}