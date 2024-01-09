package com.example.englishwritinginviews.data.db

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext

class LivesSharedPreferences(@ApplicationContext context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("LivesSharedPreferences", Context.MODE_PRIVATE)

    companion object {
        private const val LIVES_KEYS = "saved_lives"
    }

    fun getSavedLives(): Set<String> =
        sharedPreferences.getStringSet(LIVES_KEYS, emptySet()) ?: emptySet()


    fun getTheSmallestTime(): Long {
        val set = sharedPreferences.getStringSet(LIVES_KEYS, emptySet()) ?: emptySet()
        val currentTime = System.currentTimeMillis()

        set.filter {
            val time = it.toLong()
            currentTime - time >= 7200000
        }
        val longTimes = mutableListOf<Long>()
        set.forEach {
            longTimes.add(it.toLong())
        }

        return longTimes.maxOrNull() ?: 0
    }

    fun saveLife() {
        val existingSet =
            sharedPreferences.getStringSet(LIVES_KEYS, emptySet()) ?: emptySet<String>()
        val newSet = existingSet.toMutableSet()
        newSet.add(System.currentTimeMillis().toString())
        sharedPreferences.edit().putStringSet(LIVES_KEYS, newSet).apply()
    }
}
