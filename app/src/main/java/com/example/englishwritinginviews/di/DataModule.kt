package com.example.englishwritinginviews.di

import com.example.englishwritinginviews.data.ApiService
import com.example.englishwritinginviews.data.DefaultMistakesRepository
import com.example.englishwritinginviews.data.MistakesRepository
import com.example.englishwritinginviews.data.RemoteDataSource
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
    fun provideMistakesRepository(remoteDataSource: RemoteDataSource): MistakesRepository {
        return DefaultMistakesRepository(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource {
        return RemoteDataSource(apiService)
    }
}