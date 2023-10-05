package com.example.englishwritinginviews.data.api.entities

data class MistakeApiModel(
    val matches: List<Match> = listOf(Match(0, "", 0, "")),
)