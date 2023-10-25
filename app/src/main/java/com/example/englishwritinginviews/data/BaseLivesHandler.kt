package com.example.englishwritinginviews.data

import android.util.Log
import com.example.englishwritinginviews.data.db.LivesSharedPreferences
import com.example.englishwritinginviews.domain.LivesHandler

class BaseLivesHandler(
    private val livesSharedPreferences: LivesSharedPreferences
) : LivesHandler {
    override fun getAvailableLives(): Int {

        val unavailableLives = livesSharedPreferences.getSavedLives().filter {
            System.currentTimeMillis() - it.toLong() < 7200000
        }.size

        return when (unavailableLives) {
            3 -> 0
            2 -> 1
            1 -> 2
            else -> 3
        }
    }

    override fun useLife() {
        livesSharedPreferences.saveLife()
    }

    override fun isLifeAvailable(): Boolean {
        val q = getAvailableLives()
        return q > 0
    }

    // todo move to ui-layer
    override fun getTheSmallestTimeDiff(): String {
        val diff = 7200000 - (System.currentTimeMillis() - livesSharedPreferences.getTheSmallestTime())

        val seconds = diff / 1000
        val minutes = (seconds / 60) % 60
        val hours = (seconds / 3600)

        val time = StringBuilder()

        if (hours > 0) time.append("$hours hours ")
        if (minutes > 0) time.append("$minutes minutes")

        return time.toString()
    }

}