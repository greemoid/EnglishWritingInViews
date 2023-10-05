package com.example.englishwritinginviews.data.api.entities

data class Match(
    val length: Int,
    val message: String,
    val offset: Int,
    val sentence: String,
)