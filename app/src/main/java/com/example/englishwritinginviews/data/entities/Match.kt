package com.example.englishwritinginviews.data.entities

import com.example.englishwritinginviews.domain.Mistake

data class Match(
    val length: Int,
    val message: String,
    val offset: Int,
    val sentence: String,
) {
    fun toMistake(): Mistake = Mistake(message, offset, length)
}