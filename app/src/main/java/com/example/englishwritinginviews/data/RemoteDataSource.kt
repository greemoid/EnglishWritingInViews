package com.example.englishwritinginviews.data

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun fetchMistakes(answer: String) = apiService.fetchMistakes(answer)
}