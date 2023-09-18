package com.posco.feedscreentestapp.di.service

import com.example.screen_feed.FeedService
import com.posco.feedscreentestapp.di.service.restaurant.ProductRestaurantService
import com.sryang.library.entity.Feed
import com.sryang.torang_repository.repository.feed.FeedRepository
import com.sryang.torang_repository.services.RestaurantService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(ActivityComponent::class)
@Module
class ServiceModule {
    @Provides
    fun provideFeedService(
        feedRepository: FeedRepository
    ): FeedService {
        return object : FeedService {
            override suspend fun getFeeds(params: Map<String, String>): List<Feed> {
                return feedRepository.loadFeed()
            }
        }
    }

    @Singleton
    @Provides
    fun provideRestaurantService(
        productRestaurantService: ProductRestaurantService
    ): RestaurantService {
        return productRestaurantService.create()
    }
}