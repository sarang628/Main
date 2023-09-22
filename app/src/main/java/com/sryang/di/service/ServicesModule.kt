package com.sryang.torang_repository.di.service

import com.sryang.torang_repository.api.ApiRestaurant
import com.sryang.torang_repository.di.service.restaurant.ProductRestaurantService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RestaurantServiceModule {

    @Singleton
    @Provides
    fun provideRestaurantService(
        productRestaurantService: ProductRestaurantService
    ): ApiRestaurant {
        return productRestaurantService.create()
    }

}
