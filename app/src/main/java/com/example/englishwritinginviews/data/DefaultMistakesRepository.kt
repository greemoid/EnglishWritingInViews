package com.example.englishwritinginviews.data

import com.example.englishwritinginviews.data.api.AIApiService
import com.example.englishwritinginviews.data.api.BaseApiResponse
import com.example.englishwritinginviews.data.api.MistakesApiService
import com.example.englishwritinginviews.data.api.entities.ai.AIResponse
import com.example.englishwritinginviews.data.db.LocalDataSource
import com.example.englishwritinginviews.domain.Mistake
import com.example.englishwritinginviews.domain.MistakesRepository
import com.example.englishwritinginviews.domain.QuestionDomain
import com.example.englishwritinginviews.domain.WorkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DefaultMistakesRepository @Inject constructor(
    private val mistakesApiService: MistakesApiService,
    private val aiApiService: AIApiService,
    private val localDataSource: LocalDataSource
) : MistakesRepository, BaseApiResponse() {

    override suspend fun fetchMistakes(answer: String): Flow<WorkResult<List<Mistake>>> {
        return safeApiCall { mistakesApiService.fetchMistakes(answer) }
            .flowOn(Dispatchers.IO).mapMatchesToMistakes()
    }

    override fun getQuestions(filterList: Set<String>): Flow<List<QuestionDomain>> =
        localDataSource.getQuestions(filterList)

    override fun getAnsweredQuestions(): Flow<List<QuestionDomain>> =
        localDataSource.getAnsweredQuestions()

    override fun updateAnswer(
        id: Int,
        answer: String,
        answeredAt: Long,
        rating: Float
    ): QuestionDomain =
        localDataSource.updateAnswer(id, answer, answeredAt, rating)

    override suspend fun fetchAIResponse(prompt: String): Flow<WorkResult<String>> {
        return safeApiCall {
            aiApiService.fetchChatCompletion(
                requestBody = AIApiService.ChatRequestBody(
                    listOf(AIApiService.Message(content = prompt))
                )
            )
        }.flowOn(Dispatchers.IO).mapResponseToString()
    }
}





