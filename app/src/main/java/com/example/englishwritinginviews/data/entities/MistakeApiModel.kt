package com.example.englishwritinginviews.data.entities

data class MistakeApiModel(
    val matches: List<Match> = listOf(Match(0, "", 0, "")),
)