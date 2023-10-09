package com.example.myapplication.di.restaurant_detail

import android.util.Log
import android.view.Menu
import com.sryang.torang_repository.api.ApiRestaurant
import com.sryang.torang_repository.data.RestaurantDetail
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
import restaurant_information.RestaurantImages
import restaurant_information.RestaurantInfoService
import restaurant_information.RestaurantInfoUIState
import retrofit2.HttpException
import kotlin.streams.toList

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
                    val result: RestaurantDetail = apiRestaurant.getRestaurantDetail(restaurantId)
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

fun RestaurantDetail.toRestaurantInfoData(): RestaurantInfoData {
    return RestaurantInfoData(
        foodType = this.restaurant.restaurantType,
        distance = "100m(hard coded)",
        open = "영업 중(hard coded)",
        close = "오후 9:00에 영업 종료(hard coded)",
        address = this.restaurant.address,
        webSite = this.restaurant.website,
        tel = this.restaurant.tel,
        name = this.restaurant.restaurantName,
        imageUrl = this.restaurant.imgUrl1
    )
}

fun RestaurantDetail.toMenus(): List<MenuData> {
    return this.menus.stream().map {
        MenuData(
            menuName = it.menu_name,
            price = it.menu_price.toFloat(),
            url = it.menu_pic_url
        )
    }.toList()
}

fun RestaurantDetail.toRestaurantImages(): List<RestaurantImage> {
    return this.pictures.stream().map {
        RestaurantImage(
            url = it.picture_url
        )
    }.toList()
}

fun RestaurantDetail.toReviewSummaryData(): ReviewSummaryData {
    return testReviewSummaryData()
}

fun RestaurantDetail.toReviewRowData(): List<ReviewRowData> {
    return this.comments.stream().map {
        ReviewRowData(
            name = it.user_name,
            fullName = it.user_name,
            rating = 3.0f,
            comment = it.comment
        )
    }.toList()
}