package com.example.englishwritinginviews.testUtils

import com.example.englishwritinginviews.data.db.entities.QuestionDbModel
import com.example.englishwritinginviews.domain.QuestionDomain

fun createQuestionDomain(isAnswered: Boolean): QuestionDomain {
    val num = 1
    return QuestionDomain(
        id = num,
        question = "Question $num",
        answer = "Answer $num",
        difficulty = "Difficulty $num",
        color = "Color $num",
        isAnswered = isAnswered,
        answeredAt = 1111111L,
        rating = 1.0
    )
}


fun createListOfQuestionDomain(isAnswered: Boolean, number: Int): List<QuestionDomain> {
    val mutableList = mutableListOf<QuestionDomain>()
    (0..number).map { mutableList.add(createQuestionDomain(isAnswered)) }
    return mutableList
}

fun createQuestionDB(isAnswered: Boolean): QuestionDbModel {
    val num = 1
    return QuestionDbModel(
        id = num,
        question = "Question $num",
        answer = "Answer $num",
        difficulty = "Difficulty $num",
        color = "Color $num",
        isAnswered = isAnswered,
        answeredAt = 1111111L,
        rating = 1.0
    )
}


fun createListOfQuestionDB(isAnswered: Boolean, number: Int): List<QuestionDbModel> {
    val mutableList = mutableListOf<QuestionDbModel>()
    (0..number).map { mutableList.add(createQuestionDB(isAnswered)) }
    return mutableList
}