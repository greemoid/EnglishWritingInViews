package com.example.englishwritinginviews.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.englishwritinginviews.domain.QuestionDomain

@Entity(tableName = "questions")
data class QuestionDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "question") val question: String,
    @ColumnInfo(name = "answer") val answer: String,
    @ColumnInfo(name = "difficulty") val difficulty: String,
    @ColumnInfo(name = "color") val color: String,
    @ColumnInfo(name = "isAnswered") val isAnswered: Boolean,
    @ColumnInfo(name = "answeredAt") val answeredAt: Long
)

fun QuestionDbModel.toDomainModel(): QuestionDomain =
    QuestionDomain(id, question, answer, difficulty, color, isAnswered, answeredAt)

fun QuestionDomain.toDbModel(): QuestionDbModel =
    QuestionDbModel(id, question, answer, difficulty, color, isAnswered, answeredAt)
