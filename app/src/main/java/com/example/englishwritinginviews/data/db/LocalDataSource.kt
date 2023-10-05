package com.example.englishwritinginviews.data.db

import com.example.englishwritinginviews.data.db.entities.toDomainModel
import com.example.englishwritinginviews.domain.QuestionDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataSource(private val dao: QuestionDao) {

    fun getAllQuestions(): Flow<List<QuestionDomain>> {
        return try {
            dao.getAllQuestions().map { list ->
                list.map { it.toDomainModel() }
            }
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    fun updateAnswer(id: Int, answer: String, answeredAt: Long): QuestionDomain {
        return try {
            dao.updateAndGetAnswer(id = id, answer = answer, answeredAt = answeredAt)
                .toDomainModel()
        } catch (e: Exception) {
            throw Exception(e)
        }
    }
}