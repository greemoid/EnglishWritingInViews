package com.example.englishwritinginviews.di

import com.example.englishwritinginviews.data.MistakesRepository
import com.example.englishwritinginviews.domain.FetchMistakesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideFetchMistakesUseCase(mistakesRepository: MistakesRepository): FetchMistakesUseCase {
        return FetchMistakesUseCase(mistakesRepository)
    }
}