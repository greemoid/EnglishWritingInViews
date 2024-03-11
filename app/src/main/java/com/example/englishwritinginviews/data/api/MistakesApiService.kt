package com.example.englishwritinginviews.data.api

import com.example.englishwritinginviews.data.api.entities.MistakeApiModel
import com.example.englishwritinginviews.data.api.entities.ai.AIResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST


interface MistakesApiService {

    @Headers("accept: application/json")
    @FormUrlEncoded
    @POST("check")
    suspend fun fetchMistakes(
        @Field("text") text: String,
        @Field("language") language: String = "en-US",
        @Field("enabledOnly") enabledOnly: Boolean = false
    ): Response<MistakeApiModel>
}

interface AIApiService {
    @Headers("Content-Type: application/json")
    @POST("openai/v1/chat/completions")
    suspend fun fetchChatCompletion(
        @Header("Authorization") authorization: String = "Bearer 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",
        @Body requestBody: ChatRequestBody
    ): Response<AIResponse>


    data class ChatRequestBody(
        val messages: List<Message>,
        val model: String = "llama2-70b-4096"
    )

    data class Message(
        val role: String = "user",
        val content: String
    )
}


