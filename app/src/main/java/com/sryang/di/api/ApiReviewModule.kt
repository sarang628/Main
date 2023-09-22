package com.sryang.torang_repository.di.api

import com.sryang.torang_repository.api.ApiReview
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class ApiReviewModule {
    @Singleton
    @Provides
    fun provideRemoveFeedService(
        reviewServiceProduct: ReviewServiceProductImpl
    ): ApiReview {
        return reviewServiceProduct.create()
    }
}
