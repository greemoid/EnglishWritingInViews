package com.example.englishwritinginviews.data.entities

data class Rule(
    val category: Category,
    val description: String,
    val id: String,
    val isPremium: Boolean,
    val issueType: String,
    val sourceFile: String,
    val subId: String,
    val urls: List<Url>
)