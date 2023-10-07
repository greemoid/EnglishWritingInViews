package com.example.englishwritinginviews.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.englishwritinginviews.data.db.entities.QuestionDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {

    @Query("SELECT * FROM questions")
    fun getAllQuestions(): Flow<List<QuestionDbModel>>

    @Query("SELECT * FROM questions WHERE isAnswered = :isAnswered")
    fun getAnsweredQuestions(isAnswered: Boolean = true): Flow<List<QuestionDbModel>>


    @Transaction
    fun updateAndGetAnswer(
        id: Int,
        answer: String,
        isAnswered: Boolean = true,
        answeredAt: Long
    ): QuestionDbModel {
        updateAnswer(id, answer, isAnswered, answeredAt)
        return getAnswerById(id)
    }

    @Query("UPDATE questions SET answer = :answer, isAnswered = :isAnswered, answeredAt = :answeredAt WHERE id = :id")
    fun updateAnswer(
        id: Int,
        answer: String,
        isAnswered: Boolean = true,
        answeredAt: Long
    )

    @Query("SELECT * FROM questions where id = :id")
    fun getAnswerById(id: Int): QuestionDbModel


}