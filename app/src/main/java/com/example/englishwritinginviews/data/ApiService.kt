package com.example.englishwritinginviews.data

import com.example.englishwritinginviews.data.entities.Mistake
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiService {

    @Headers("accept: application/json")
    @FormUrlEncoded
    @POST("check")
    suspend fun fetchMistakes(
        @Field("text") text: String,
        @Field("language") language: String = "en-US",
        @Field("enabledOnly") enabledOnly: Boolean = false
    ): Response<Mistake>
}

