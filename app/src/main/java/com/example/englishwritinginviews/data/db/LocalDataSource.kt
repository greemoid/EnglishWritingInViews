package com.example.englishwritinginviews.data.db

import com.example.englishwritinginviews.data.db.entities.toDomainModel
import com.example.englishwritinginviews.domain.QuestionDomain
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val dao: QuestionDao) : BaseFlowResponse() {

    fun getQuestions(filterList: Set<String>): Flow<List<QuestionDomain>> =
        if (filterList.isEmpty()) {
            safeFlowCall { dao.getAllQuestions() }
        } else {
            safeFlowCall { dao.getFilteredQuestions(filterList) }
        }


    fun getAnsweredQuestions(): Flow<List<QuestionDomain>> =
        safeFlowCall { dao.getAnsweredQuestions() }

    fun updateAnswer(
        id: Int,
        answer: String,
        answeredAt: Long,
        rating: Float
    ): QuestionDomain {
        return dao.updateAndGetAnswer(
            id = id,
            answer = answer,
            answeredAt = answeredAt,
            rating = rating
        ).toDomainModel()
    }
}


