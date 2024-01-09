package com.example.englishwritinginviews.presentation.core

import kotlinx.coroutines.flow.Flow


interface ConnectionObserver {

    fun observe(): Flow<Status>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }
}
