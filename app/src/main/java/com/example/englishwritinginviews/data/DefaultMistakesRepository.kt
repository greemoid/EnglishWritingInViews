package com.example.englishwritinginviews.data

import com.example.englishwritinginviews.data.api.ApiService
import com.example.englishwritinginviews.data.api.BaseApiResponse
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
    private val apiService: ApiService,
    private val localDataSource: LocalDataSource
) : MistakesRepository, BaseApiResponse() {
    override suspend fun fetchMistakes(answer: String): Flow<WorkResult<List<Mistake>>> {
        return safeApiCall { apiService.fetchMistakes(answer) }
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
}





