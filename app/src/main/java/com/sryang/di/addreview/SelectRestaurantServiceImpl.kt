package com.sryang.addreview.di.addreview

import com.sryang.addreview.data.SelectRestaurantData
import com.sryang.addreview.usecase.SelectRestaurantUseCase
import com.sryang.torang_repository.api.ApiRestaurant
import com.sryang.torang_repository.data.remote.response.RemoteRestaurant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlin.streams.toList

@InstallIn(SingletonComponent::class)
@Module
class SelectRestaurantServiceImpl {
    @Provides
    fun provideSelectRestaurantService(apiRestaurant: ApiRestaurant): SelectRestaurantUseCase {
        return object : SelectRestaurantUseCase {
            override suspend fun getRestaurant(): List<SelectRestaurantData> {
                val result = apiRestaurant.getAllRestaurant(HashMap())
                return result.stream().map { it.toSelectRestaurantData() }.toList()
            }

        }
    }
}

fun RemoteRestaurant.toSelectRestaurantData(): SelectRestaurantData {
    return SelectRestaurantData(
        restaurantId = this.restaurantId,
        restaurantName = this.restaurantName,
        address = this.address
    )
}