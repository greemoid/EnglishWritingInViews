package com.example.englishwritinginviews.domain

import kotlinx.coroutines.flow.Flow

interface MistakesRepository {
    suspend fun fetchMistakes(answer: String): Flow<WorkResult<List<Mistake>>>

    fun getAllQuestions(): Flow<List<QuestionDomain>>

    fun getAnsweredQuestions(): Flow<List<QuestionDomain>>

    fun updateAnswer(id: Int, answer: String, answeredAt: Long): QuestionDomain

}