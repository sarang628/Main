package com.posco.feedscreentestapp.di.remote

import com.sryang.torang_repository.di.service.feed.FeedServiceProductImpl
import com.sryang.torang_repository.services.RemoteFeedServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RemoteFeedModule {
    @Singleton
    @Provides
    fun provideRemoveFeedService(
        productFeedServiceImpl: FeedServiceProductImpl
    ): RemoteFeedServices {
        return productFeedServiceImpl.create()
    }
}