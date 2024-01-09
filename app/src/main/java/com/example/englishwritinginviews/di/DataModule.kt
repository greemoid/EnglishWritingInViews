package com.example.englishwritinginviews.di

import android.content.Context
import com.example.englishwritinginviews.data.BaseLivesHandler
import com.example.englishwritinginviews.data.BaseLoginRepository
import com.example.englishwritinginviews.data.DefaultMistakesRepository
import com.example.englishwritinginviews.data.api.ApiService
import com.example.englishwritinginviews.data.db.LivesSharedPreferences
import com.example.englishwritinginviews.data.db.LocalDataSource
import com.example.englishwritinginviews.data.db.QuestionDao
import com.example.englishwritinginviews.domain.LivesHandler
import com.example.englishwritinginviews.domain.LoginRepository
import com.example.englishwritinginviews.domain.MistakesRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideMistakesRepository(
        apiService: ApiService,
        localDataSource: LocalDataSource
    ): MistakesRepository {
        return DefaultMistakesRepository(apiService, localDataSource)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(dao: QuestionDao): LocalDataSource {
        return LocalDataSource(dao)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(auth: FirebaseAuth): LoginRepository =
        BaseLoginRepository(auth)

    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    @Singleton
    fun providesLivesHandler(@ApplicationContext context: Context): LivesHandler =
        BaseLivesHandler(LivesSharedPreferences(context))
}
