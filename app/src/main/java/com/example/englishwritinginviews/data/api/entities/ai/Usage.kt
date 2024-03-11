package com.example.englishwritinginviews.data.api.entities.ai

data class Usage(
    val completion_time: Double,
    val completion_tokens: Int,
    val prompt_time: Double,
    val prompt_tokens: Int,
    val total_time: Double,
    val total_tokens: Int
)