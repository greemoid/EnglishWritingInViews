package com.example.englishwritinginviews.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.englishwritinginviews.data.db.entities.QuestionDbModel

@Database(entities = [QuestionDbModel::class], version = 9)
abstract class QuestionsDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
}