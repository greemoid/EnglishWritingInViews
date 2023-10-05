package com.example.englishwritinginviews.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.englishwritinginviews.data.db.entities.QuestionDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {

    @Query("UPDATE questions SET answer = :answer, isAnswered = :isAnswered, answeredAt = :answeredAt WHERE id = :id")
    fun updateAnswer(
        id: Int,
        answer: String,
        isAnswered: Boolean = true,
        answeredAt: Long
    )

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

    @Query("SELECT * FROM questions where id = :id")
    fun getAnswerById(id: Int): QuestionDbModel

    @Query("SELECT * FROM questions")
    fun getAllQuestions(): Flow<List<QuestionDbModel>>

}