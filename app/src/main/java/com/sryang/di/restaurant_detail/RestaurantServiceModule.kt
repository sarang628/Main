package com.example.myapplication.di.restaurant_detail

import android.util.Log
import com.sarang.base_feed.uistate.FeedBottomUIState
import com.sarang.base_feed.uistate.FeedTopUIState
import com.sarang.base_feed.uistate.FeedUiState
import com.sryang.torang_repository.api.ApiRestaurant
import com.sryang.torang_repository.data.RestaurantDetail
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.sr.restaurant.restaurant.data.HoursOfOperation
import com.sr.restaurant.restaurant.data.MenuData
import com.sr.restaurant.restaurant.data.RestaurantImage
import com.sr.restaurant.restaurant.data.RestaurantInfoData
import com.sr.restaurant.restaurant.data.ReviewRowData
import com.sr.restaurant.restaurant.data.ReviewSummaryData
import com.sr.restaurant.restaurant.data.testReviewSummaryData
import com.sr.restaurant.restaurant.RestaurantInfoService
import com.sr.restaurant.restaurant.data.FeedData
import com.sr.restaurant.restaurant.viewmodel.RestaurantInfoUIState
import com.sryang.torang_repository.api.ApiFeed
import com.sryang.torang_repository.api.ApiReview
import com.sryang.torang_repository.data.entity.ReviewAndImageEntity
import com.sryang.torang_repository.data.remote.response.RemoteFeed
import retrofit2.HttpException
import kotlin.streams.toList

@InstallIn(SingletonComponent::class)
@Module
class RestaurantServiceModule {
    @Provides
    fun provideRestaruantService(
        apiRestaurant: ApiRestaurant,
        apiReview: ApiReview
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
                        reviewSummaryData = result.toReviewSummaryData(),
                        reviews = ArrayList()
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

            override suspend fun loadReviews(restaurantId: Int): List<FeedData> {
                return apiReview.getReviews(restaurantId).stream().map { it.toFeedData() }.toList()
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
        distance = "100m",
        open = "영업 중",
        close = "오후 9:00에 영업 종료",
        address = this.restaurant.address,
        webSite = this.restaurant.website,
        tel = this.restaurant.tel,
        name = this.restaurant.restaurantName,
        imageUrl = this.restaurant.imgUrl1,
        hoursOfOperation = this.hoursOfOperations.stream().map { it.toHoursOfOperation() }.toList()
    )
}

fun com.sryang.torang_repository.data.HoursOfOperation.toHoursOfOperation(): HoursOfOperation {
    return HoursOfOperation(
        day = this.day,
        startTime = this.start_time,
        endTime = this.end_time
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

fun FeedData.toFeedUiState(): FeedUiState {
    return FeedUiState(
        reviewId = this.reviewId,
        itemFeedBottomUiState = this.toFeedBottomUIState(),
        itemFeedTopUiState = this.toFeedTopUIState(),
        reviewImages = this.reviewImages
    )
}

fun FeedData.toFeedBottomUIState(
): FeedBottomUIState {
    return FeedBottomUIState(
        reviewId = this.reviewId,
        likeAmount = this.likeAmount,
        commentAmount = this.commentAmount,
        author = this.author,
        author1 = this.author1,
        author2 = this.author2,
        comment = this.comment,
        comment1 = this.comment1,
        comment2 = this.comment2,
        isLike = this.isLike,
        isFavorite = this.isFavorite,
        visibleLike = this.visibleLike,
        visibleComment = this.visibleComment,
        contents = this.contents
    )
}

fun FeedData.toFeedTopUIState(): FeedTopUIState {
    return FeedTopUIState(
        reviewId = this.reviewId,
        userId = this.userId,
        name = this.name,
        restaurantName = this.restaurantName,
        rating = this.rating,
        profilePictureUrl = this.profilePictureUrl,
        restaurantId = restaurantId
    )
}

fun RemoteFeed.toFeedData(): FeedData {
    return FeedData(
        reviewId = this.reviewId,
        userId = this.user.userId,
        name = this.user.userName,
        restaurantName = this.restaurant.restaurantName,
        rating = this.rating,
        profilePictureUrl = this.user.profilePicUrl,
        likeAmount = this.like_amount,
        commentAmount = this.comment_amount,
        author = "",
        author1 = "",
        author2 = "",
        comment = "",
        comment1 = "",
        comment2 = "",
        isLike = this.like != null,
        isFavorite = this.favorite != null,
        visibleLike = false,
        visibleComment = false,
        contents = this.contents,
        reviewImages = this.pictures.stream().map { it.picture_url }.toList(),
        restaurantId = this.restaurant.restaurantId
    )
}