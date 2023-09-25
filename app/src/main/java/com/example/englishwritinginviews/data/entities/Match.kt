package com.example.englishwritinginviews.data.entities

data class Match(
    val context: Context,
    val contextForSureMatch: Int,
    val ignoreForIncompleteSentence: Boolean,
    val length: Int,
    val message: String,
    val offset: Int,
    val replacements: List<Replacement>,
    val rule: Rule,
    val sentence: String,
    val shortMessage: String,
    val type: Type
)