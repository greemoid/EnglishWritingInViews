package com.example.englishwritinginviews.presentation.core

import android.graphics.Color
import android.widget.TextView

fun TextView.setDifficultyColor(difficultyColor: String) {
    val color = when (difficultyColor) {
        "RED" -> "#FF0000"
        "YELLOW" -> "#ffff00"
        "GREEN" -> "#00ff00"
        else -> "#FFFFFF"
    }
    this.setTextColor(Color.parseColor(color))
}