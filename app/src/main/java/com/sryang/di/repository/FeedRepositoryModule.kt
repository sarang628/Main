package com.sryang.torang_repository.di.repository

import com.sryang.torang_repository.repository.feed.FeedRepository
import com.sryang.torang_repository.repository.feed.FeedRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FeedRepositoryModule {
    @Binds
    abstract fun provideFeedRepository(feedRepositoryImpl: FeedRepositoryImpl): FeedRepository
}