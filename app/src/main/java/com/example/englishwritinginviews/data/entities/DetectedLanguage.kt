package com.example.englishwritinginviews.data.entities

data class DetectedLanguage(
    val code: String,
    val confidence: Double,
    val name: String,
    val source: String
)