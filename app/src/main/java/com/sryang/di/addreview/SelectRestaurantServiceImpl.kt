package com.sryang.addreview.di.addreview

import com.sryang.library.selectrestaurant.SelectRestaurantData
import com.sryang.library.selectrestaurant.SelectRestaurantService
import com.sryang.torang_repository.api.ApiRestaurant
import com.sryang.torang_repository.data.remote.response.RemoteRestaurant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import kotlin.streams.toList

@InstallIn(SingletonComponent::class)
@Module
class SelectRestaurantServiceImpl {
    @Provides
    fun provideSelectRestaurantService(apiRestaurant: ApiRestaurant): SelectRestaurantService {
        return object : SelectRestaurantService {
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