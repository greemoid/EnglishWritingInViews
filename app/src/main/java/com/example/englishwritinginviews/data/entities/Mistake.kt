package com.example.englishwritinginviews.data.entities

data class Mistake(
    val language: Language,
    val matches: List<Match>,
    val sentenceRanges: List<List<Int>>,
    val software: Software,
    val warnings: Warnings

)