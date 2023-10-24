package com.example.englishwritinginviews.domain

data class Mistake(
    val textOfMistake: String,
    val startOfMistake: Int,
    val lengthOfMistake: Int,
)
