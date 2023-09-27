package com.example.englishwritinginviews.data.entities

data class Mistake(
    val matches: List<Match> = listOf(Match(0, "", 0, "")),
)