package com.example.englishwritinginviews.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
    fun provideDatabase(@ApplicationContext context: Context): QuestionsDatabase {
        /*val MIGRATION_12_13: Migration = object : Migration(12, 13) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE question_new (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                            "question TEXT NOT NULL," +
                            "answer TEXT NOT NULL," +
                            "difficulty TEXT NOT NULL," +
                            "color TEXT NOT NULL," +
                            "isAnswered INTEGER NOT NULL," +
                            "answeredAt INTEGER NOT NULL," +
                            "rating REAL NOT NULL DEFAULT 0.0)"
                )

                // Copy the data from the old table to the new table
                database.execSQL(
                    "INSERT INTO question_new (" +
                            "id, question, answer, difficulty, color, isAnswered, answeredAt) " +
                            "SELECT id, question, answer, difficulty, color, isAnswered, answeredAt " +
                            "FROM questions"
                )

                // Remove the old table
                database.execSQL("DROP TABLE questions")

                // Change the new table name to the old table name
                database.execSQL("ALTER TABLE question_new RENAME TO questions")
            }
        }*/
        return Room.databaseBuilder(context, QuestionsDatabase::class.java, "questions")
            .createFromAsset("questions.db")
            .allowMainThreadQueries()
//            .addMigrations(MIGRATION_12_13)
//            .fallbackToDestructiveMigration()  //i can on it when i need :(
            .build()
    }


    @Provides
    @Singleton
    fun provideQuestionsDao(database: QuestionsDatabase): QuestionDao =
        database.questionDao()
}
