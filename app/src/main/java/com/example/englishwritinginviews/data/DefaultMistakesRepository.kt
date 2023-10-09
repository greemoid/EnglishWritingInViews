package com.example.englishwritinginviews.data

import com.example.englishwritinginviews.data.api.BaseApiResponse
import com.example.englishwritinginviews.data.api.RemoteDataSource
import com.example.englishwritinginviews.data.api.entities.MistakeApiModel
import com.example.englishwritinginviews.data.db.LocalDataSource
import com.example.englishwritinginviews.domain.Mistake
import com.example.englishwritinginviews.domain.MistakesRepository
import com.example.englishwritinginviews.domain.QuestionDomain
import com.example.englishwritinginviews.domain.WorkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultMistakesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : MistakesRepository, BaseApiResponse() {
    override suspend fun fetchMistakes(answer: String): Flow<WorkResult<List<Mistake>>> {
        return safeApiCall { remoteDataSource.fetchMistakes(answer) }
            .flowOn(Dispatchers.IO).mapMatchesToMistakes()
    }

    override fun getQuestions(filterList: List<String>): Flow<List<QuestionDomain>> = localDataSource.getQuestions(filterList)

    override fun getAnsweredQuestions(): Flow<List<QuestionDomain>> =
        localDataSource.getAnsweredQuestions()

    override fun updateAnswer(id: Int, answer: String, answeredAt: Long): QuestionDomain =
        localDataSource.updateAnswer(id, answer, answeredAt)
}

private fun Flow<WorkResult<MistakeApiModel>>.mapMatchesToMistakes(): Flow<WorkResult<List<Mistake>>> {
    return this.map { workResult ->
        when (workResult) {
            is WorkResult.Success -> {
                val mistakesApiModel = workResult.data
                val mistakes = mistakesApiModel?.let { mapMistakesApiModelToMistakes(it) }
                WorkResult.Success(mistakes)
            }

            is WorkResult.Error -> {
                workResult
            }

            is WorkResult.Loading -> {
                WorkResult.Loading()
            }
        }
    } as Flow<WorkResult<List<Mistake>>>
}

private fun mapMistakesApiModelToMistakes(mistakesApiModel: MistakeApiModel): List<Mistake> {
    return mistakesApiModel.matches.map { match ->
        Mistake(
            textOfMistake = match.message,
            startOfMistake = match.offset,
            lengthOfMistake = match.length,
        )
    }
}



