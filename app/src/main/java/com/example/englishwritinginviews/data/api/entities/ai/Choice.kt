package com.example.englishwritinginviews.data.api.entities.ai

data class Choice(
    val finish_reason: String,
    val index: Int,
    val logprobs: Any,
    val message: Message
)