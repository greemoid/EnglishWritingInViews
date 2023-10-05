package com.example.englishwritinginviews.di

import com.example.englishwritinginviews.domain.FetchMistakesUseCase
import com.example.englishwritinginviews.domain.FetchQuestionsUseCase
import com.example.englishwritinginviews.domain.MistakesRepository
import com.example.englishwritinginviews.domain.UpdateAnswerUseCase
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

    @Provides
    @Singleton
    fun provideFetchQuestionsUseCase(mistakesRepository: MistakesRepository): FetchQuestionsUseCase {
        return FetchQuestionsUseCase(mistakesRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateAnswerUseCase(mistakesRepository: MistakesRepository): UpdateAnswerUseCase {
        return UpdateAnswerUseCase(mistakesRepository)
    }
}