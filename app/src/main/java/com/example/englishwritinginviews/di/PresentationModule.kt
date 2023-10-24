package com.example.englishwritinginviews.di

import android.content.Context
import com.example.englishwritinginviews.presentation.core.BaseResourceManager
import com.example.englishwritinginviews.presentation.core.ConnectionObserver
import com.example.englishwritinginviews.presentation.core.NetworkConnectionObserver
import com.example.englishwritinginviews.presentation.core.ResourceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    @Provides
    @Singleton
    fun provideConnectionObserver(@ApplicationContext context: Context): ConnectionObserver =
        NetworkConnectionObserver(context)

    @Provides
    @Singleton
    fun provideResourceManager(@ApplicationContext context: Context): ResourceManager =
        BaseResourceManager(context)
}