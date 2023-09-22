package com.posco.feedscreentestapp.di.feed

import com.example.screen_feed.FeedService
import com.sarang.base_feed.uistate.FeedBottomUIState
import com.sarang.base_feed.uistate.FeedTopUIState
import com.sarang.base_feed.uistate.FeedUiState
import com.sryang.torang_repository.data.remote.response.RemoteFeed
import com.sryang.torang_repository.repository.feed.FeedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlin.streams.toList

@InstallIn(SingletonComponent::class)
@Module
class FeedServiceModule {
    @Provides
    fun provideFeedService(
        feedRepository: FeedRepository
    ): FeedService {
        return object : FeedService {
            override suspend fun getFeeds(params: Map<String, String>): List<FeedUiState> {
                return feedRepository.loadFeed().stream().map {
                    FeedUiState(
                        reviewId = it.reviewId,
                        itemFeedBottomUiState = it.toFeedBottomUiState(),
                        itemFeedTopUiState = it.toFeedTopUiState(),
                        reviewImages = it.pictures.stream().map { it.picture_url }.toList()
                    )
                }.toList()
            }
        }
    }
}

fun RemoteFeed.toFeedBottomUiState(): FeedBottomUIState {
    return FeedBottomUIState(
        reviewId = this.reviewId,
        likeAmount = this.like_amount ?: 0,
        commentAmount = this.comment_amount ?: 0,
        author = "",
        author1 = "",
        author2 = "",
        comment = "",
        comment1 = "",
        comment2 = "",
        isLike = this.like?.isLike ?: false,
        isFavorite = this.favorite?.isFavority ?: false,
        visibleLike = true,
        visibleComment = true,
        contents = this.contents ?: ""
    )
}

fun RemoteFeed.toFeedTopUiState(): FeedTopUIState {
    return FeedTopUIState(
        reviewId = this.reviewId,
        name = this.user?.userName ?: "",
        profilePictureUrl = this.user?.profilePicUrl ?: "",
        rating = this.rating ?: 0f,
        restaurantName = this.restaurant?.restaurantName ?: "",
        userId = this.user?.userId ?: 0
    )
}