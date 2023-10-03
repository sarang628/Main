package com.example.myapplication.di.restaurant_detail

import android.util.Log
import android.view.Menu
import com.sryang.torang_repository.api.ApiRestaurant
import com.sryang.torang_repository.data.remote.response.RemoteRestaurant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import data.MenuData
import data.RestaurantImage
import data.RestaurantInfoData
import data.ReviewRowData
import data.ReviewSummaryData
import data.testMenuData
import data.testRestaurantImage
import data.testReviewRowData
import data.testReviewSummaryData
import restaurant_information.RestaurantInfoService
import restaurant_information.RestaurantInfoUIState
import retrofit2.HttpException

@InstallIn(SingletonComponent::class)
@Module
class RestaurantServiceModule {
    @Provides
    fun provideRestaruantService(
        apiRestaurant: ApiRestaurant
    ): RestaurantInfoService {
        return object : RestaurantInfoService {
            override suspend fun loadRestaurant(restaurantId: Int): RestaurantInfoUIState {
                try {
                    val result: RemoteRestaurant = apiRestaurant.getRestaurantById(restaurantId)
                    return RestaurantInfoUIState(
                        restaurantInfoData = result.toRestaurantInfoData(),
                        menus = result.toMenus(),
                        restaurantImage = result.toRestaurantImages(),
                        reviewRowData = result.toReviewRowData(),
                        reviewSummaryData = result.toReviewSummaryData()
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

fun RemoteRestaurant.toRestaurantInfoData(): RestaurantInfoData {
    return RestaurantInfoData(
        foodType = this.restaurantType,
        distance = "100m(hard coded)",
        open = "영업 중(hard coded)",
        close = "오후 9:00에 영업 종료(hard coded)",
        address = this.address,
        webSite = this.website,
        tel = this.tel,
        name = this.restaurantName,
        imageUrl = this.imgUrl1
    )
}

fun RemoteRestaurant.toMenus(): List<MenuData> {
    return ArrayList<MenuData>().apply {
        add(testMenuData())
        add(testMenuData())
        add(testMenuData())
        add(testMenuData())
        add(testMenuData())
    }
}

fun RemoteRestaurant.toRestaurantImages(): List<RestaurantImage> {
    return ArrayList<RestaurantImage>().apply {
        add(testRestaurantImage())
        add(testRestaurantImage())
        add(testRestaurantImage())
        add(testRestaurantImage())
        add(testRestaurantImage())
        add(testRestaurantImage())
    }
}

fun RemoteRestaurant.toReviewSummaryData(): ReviewSummaryData {
    return testReviewSummaryData()
}

fun RemoteRestaurant.toReviewRowData(): List<ReviewRowData> {
    return ArrayList<ReviewRowData>().apply {
        add(testReviewRowData())
        add(testReviewRowData())
        add(testReviewRowData())
        add(testReviewRowData())
        add(testReviewRowData())
        add(testReviewRowData())
        add(testReviewRowData())
        add(testReviewRowData())
        add(testReviewRowData())
        add(testReviewRowData())
        add(testReviewRowData())
        add(testReviewRowData())
        add(testReviewRowData())
    }
}