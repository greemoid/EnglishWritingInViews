package com.example.englishwritinginviews.domain

import java.io.Serializable

data class QuestionDomain(
    val id: Int,
    val question: String,
    val answer: String,
    val difficulty: String,
    val color: String,
    val isAnswered: Boolean,
    val answeredAt: Long
) : Serializable
