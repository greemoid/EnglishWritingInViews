package com.example.englishwritinginviews.data.db

import android.util.Log
import com.example.englishwritinginviews.data.db.entities.QuestionDbModel
import com.example.englishwritinginviews.data.db.entities.toDomainModel
import com.example.englishwritinginviews.domain.QuestionDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

class LocalDataSource(private val dao: QuestionDao) {

    fun getQuestions(filterList: Set<String>): Flow<List<QuestionDomain>> =
        safeFlowCall {
            if (filterList.isEmpty()) {
                dao.getAllQuestions()
            } else {
                dao.getFilteredQuestions(filterList)
            }
        }

    fun getAnsweredQuestions(): Flow<List<QuestionDomain>> =
        safeFlowCall { dao.getAnsweredQuestions() }

    fun updateAnswer(id: Int, answer: String, answeredAt: Long): QuestionDomain {
        return try {
            dao.updateAndGetAnswer(id = id, answer = answer, answeredAt = answeredAt)
                .toDomainModel()
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    private inline fun safeFlowCall(crossinline flowFunction: () -> Flow<List<QuestionDbModel>>): Flow<List<QuestionDomain>> {
        return channelFlow {
            try {

                flowFunction().collect {
                    send(it.map { model -> model.toDomainModel() })
                }

            } catch (e: Exception) {
                Log.d("asac", e.toString())
            }
        }
    }

}