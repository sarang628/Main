package com.example.screen_main.di.remote

import com.sryang.torang_repository.di.service.feed.FeedServiceProductImpl
import com.sryang.torang_repository.di.service.review.ReviewServiceProductImpl
import com.sryang.torang_repository.services.RemoteFeedServices
import com.sryang.torang_repository.services.RemoteReviewService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RemoteReviewModule {

    @Singleton
    @Provides
    fun provideRemoveFeedService(
        reviewServiceProduct: ReviewServiceProductImpl
    ): RemoteReviewService {
        return reviewServiceProduct.create()
    }
}
