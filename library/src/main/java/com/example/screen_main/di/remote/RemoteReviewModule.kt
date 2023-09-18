package com.posco.feedscreentestapp.di.remote

import com.sryang.torang_repository.di.service.review.ReviewServiceProductImpl
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
//        reviewServiceProduct: ReviewServiceLocalImpl
    ): RemoteReviewService {
        return reviewServiceProduct.create()
    }
}
