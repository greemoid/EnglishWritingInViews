package com.example.englishwritinginviews.di

import com.example.englishwritinginviews.data.DefaultMistakesRepository
import com.example.englishwritinginviews.data.api.ApiService
import com.example.englishwritinginviews.data.api.RemoteDataSource
import com.example.englishwritinginviews.data.db.LocalDataSource
import com.example.englishwritinginviews.data.db.QuestionDao
import com.example.englishwritinginviews.domain.MistakesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideMistakesRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): MistakesRepository {
        return DefaultMistakesRepository(remoteDataSource, localDataSource)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource {
        return RemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(dao: QuestionDao): LocalDataSource {
        return LocalDataSource(dao)
    }
}