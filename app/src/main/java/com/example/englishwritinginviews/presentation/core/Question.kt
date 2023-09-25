package com.example.englishwritinginviews.presentation.core

import com.example.englishwritinginviews.presentation.listOfQuestions.ListOfQuestionsFragment
import java.io.Serializable


data class Question(
    val id: Int,
    val text: String?,
    val difficulty: ListOfQuestionsFragment.Difficulty,
    val isChecked: Boolean
) : Serializable