package com.example.englishwritinginviews.domain

import kotlinx.coroutines.flow.Flow


interface LivesHandler {
    fun getAvailableLives(): Int
    fun useLife()
    fun isLifeAvailable(): Boolean
    fun getTheSmallestTimeDiff(): String
}