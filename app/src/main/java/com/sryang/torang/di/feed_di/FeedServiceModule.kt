package com.sryang.torang.di.feed_di

import com.sryang.torang.data1.FeedData
import com.sryang.torang.usecase.AddFavoriteUseCase
import com.sryang.torang.usecase.AddLikeUseCase
import com.sryang.torang.usecase.DeleteFavoriteUseCase
import com.sryang.torang.usecase.DeleteLikeUseCase
import com.sryang.torang.usecase.FeedRefreshUseCase
import com.sryang.torang.usecase.GetFeedFlowUseCase
import com.sryang.torang_repository.repository.FeedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * DomainLayer
 * DataLayer 과 UILayer을 연결
 */
@InstallIn(SingletonComponent::class)
@Module
class FeedServiceModule {
    @Provides
    fun provideFeedRefreshUseCase(
        feedRepository: FeedRepository
    ): FeedRefreshUseCase {
        return object : FeedRefreshUseCase {
            override suspend fun invoke() {
                feedRepository.loadFeed()
            }
        }
    }

    @Provides
    fun provideAddLikeUseCase(
        feedRepository: FeedRepository
    ): AddLikeUseCase {
        return object : AddLikeUseCase {
            override suspend fun invoke(reviewId: Int) {
                feedRepository.addLike(reviewId)
            }
        }
    }

    @Provides
    fun provideDeleteLikeUseCase(
        feedRepository: FeedRepository
    ): DeleteLikeUseCase {
        return object : DeleteLikeUseCase {
            override suspend fun invoke(reviewId: Int) {
                feedRepository.deleteFavorite(reviewId)
            }
        }
    }

    @Provides
    fun provideAddFavoriteUseCase(
        feedRepository: FeedRepository
    ): AddFavoriteUseCase {
        return object : AddFavoriteUseCase {
            override suspend fun invoke(reviewId: Int) {
                feedRepository.addFavorite(reviewId)
            }
        }
    }

    @Provides
    fun provideDeleteFavoriteUseCase(
        feedRepository: FeedRepository
    ): DeleteFavoriteUseCase {
        return object : DeleteFavoriteUseCase {
            override suspend fun invoke(reviewId: Int) {
                feedRepository.deleteFavorite(reviewId)
            }
        }
    }

    @Provides
    fun provideGetFeedFlowUseCase(
        feedRepository: FeedRepository
    ): GetFeedFlowUseCase {
        return object : GetFeedFlowUseCase {
            override suspend fun invoke(): Flow<List<FeedData>> {
                return feedRepository.feeds.map {
                    it.map { it.toFeedData() }
                }
            }
        }
    }
}