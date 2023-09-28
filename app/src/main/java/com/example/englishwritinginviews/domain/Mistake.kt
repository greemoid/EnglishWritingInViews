package com.example.englishwritinginviews.domain

data class Mistake(
    private val textOfMistake: String,
    private val startOfMistake: Int,
    private val lengthOfMistake: Int,
)
