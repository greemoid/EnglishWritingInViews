package com.example.englishwritinginviews.presentation.core

import androidx.annotation.StringRes

interface ResourceManager {
    fun getString(@StringRes stringRes: Int): String
}