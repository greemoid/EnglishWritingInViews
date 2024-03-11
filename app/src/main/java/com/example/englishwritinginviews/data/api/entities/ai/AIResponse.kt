package com.example.englishwritinginviews.data.api.entities.ai

data class AIResponse(
    val choices: List<Choice> = emptyList(),
    val created: Int = 0,
    val id: String = "",
    val model: String = "",
    val `object`: String = "",
    val system_fingerprint: Any = "",
    val usage: Usage = Usage(0.0, 0, 0.0, 0, 0.0, 0)
)