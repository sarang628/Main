package com.example.myapplication.di.restaurant_detail

import android.util.Log
import com.sryang.torang_repository.api.ApiRestaurant
import com.sryang.torang_repository.data.remote.response.RemoteRestaurant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import data.RestaurantInfoData
import restaurant_information.RestaurantInfoService
import retrofit2.HttpException

@InstallIn(SingletonComponent::class)
@Module
class RestaurantServiceModule {
    @Provides
    fun provideRestaruantService(
        apiRestaurant: ApiRestaurant
    ): RestaurantInfoService {
        return object : RestaurantInfoService {
            override suspend fun loadRestaurant(restaurantId: Int): RestaurantInfoData {
                try {
                    val result: RemoteRestaurant = apiRestaurant.getRestaurantById(restaurantId)
                    return RestaurantInfoData(
                        foodType = result.restaurantType,
                        distance = "100m(hard coded)",
                        open = "영업 중(hard coded)",
                        close = "오후 9:00에 영업 종료(hard coded)",
                        address = result.address,
                        webSite = result.website,
                        tel = result.tel,
                        name = result.restaurantName,
                        imageUrl = result.imgUrl1
                    )
                } catch (e: HttpException) {
                    val message = e.errorMessage()
                    Log.e("RestaurantServiceModule", message)
                    throw Exception(message)
                } catch (e: Exception) {
                    Log.e("RestaurantServiceModule", e.toString())
                }
                throw Exception("알 수 없는 오류가 발생했습니다.")
            }
        }
    }
}

fun HttpException.errorMessage(): String {
    return this.response()?.errorBody()?.string() ?: "알 수 없는 오류가 발생했습니다."
}