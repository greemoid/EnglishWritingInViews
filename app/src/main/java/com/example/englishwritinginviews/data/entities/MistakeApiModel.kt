package com.example.englishwritinginviews.data.entities

import com.example.englishwritinginviews.domain.ListOfMistakes

data class MistakeApiModel(
    val matches: List<Match> = listOf(Match(0, "", 0, "")),
) {
    fun toListOfMistakes(): ListOfMistakes = ListOfMistakes(listOf())
}