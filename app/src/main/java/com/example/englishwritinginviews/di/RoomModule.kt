package com.example.englishwritinginviews.di

import android.content.Context
import androidx.room.Room
import com.example.englishwritinginviews.data.db.QuestionDao
import com.example.englishwritinginviews.data.db.QuestionsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): QuestionsDatabase =
        Room.databaseBuilder(context, QuestionsDatabase::class.java, "questions")
            .createFromAsset("questions.db")
            .allowMainThreadQueries()
            //.fallbackToDestructiveMigration()  //i can on it when i need :(
            .build()

    @Provides
    @Singleton
    fun provideQuestionsDao(database: QuestionsDatabase): QuestionDao =
        database.questionDao()
}
