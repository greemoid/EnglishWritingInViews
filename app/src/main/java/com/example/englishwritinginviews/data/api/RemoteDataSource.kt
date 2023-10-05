package com.example.englishwritinginviews.data.api

import com.example.englishwritinginviews.data.api.ApiService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun fetchMistakes(answer: String) = apiService.fetchMistakes(answer)
}