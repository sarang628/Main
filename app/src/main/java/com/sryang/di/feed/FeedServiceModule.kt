package com.posco.feedscreentestapp.di.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.screen_feed.CommentData
import com.example.screen_feed.FeedData
import com.example.screen_feed.FeedService
import com.example.screen_feed.FeedsViewModel
import com.example.screen_feed._FeedsScreen
import com.sarang.base_feed.ui.Feeds
import com.sarang.base_feed.ui.TorangToolbar
import com.sarang.base_feed.uistate.FeedBottomUIState
import com.sarang.base_feed.uistate.FeedTopUIState
import com.sarang.base_feed.uistate.FeedUiState
import com.sryang.library.CommentBottomSheetDialog
import com.sryang.library.CommentItemUiState
import com.sryang.library.FeedMenuBottomSheetDialog
import com.sryang.library.ShareBottomSheetDialog
import com.sryang.torang_repository.data.RemoteComment
import com.sryang.torang_repository.data.entity.FeedEntity
import com.sryang.torang_repository.data.remote.response.RemoteFeed
import com.sryang.torang_repository.repository.FeedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.streams.toList

@InstallIn(SingletonComponent::class)
@Module
class FeedServiceModule {
    @Provides
    fun provideFeedService(
        feedRepository: FeedRepository
    ): FeedService {
        return object : FeedService {
            override suspend fun getFeeds() {
                feedRepository.loadFeed()
            }

            override val feeds1: Flow<List<FeedData>>
                get() = feedRepository.feeds.map { it ->
                    it.stream().map {
                        FeedData(
                            reviewId = it.review.reviewId,
                            userId = it.review.userId,
                            name = it.review.userName,
                            restaurantName = it.review.restaurantName,
                            rating = it.review.rating,
                            profilePictureUrl = it.review.profilePicUrl,
                            likeAmount = it.review.likeAmount,
                            commentAmount = it.review.commentAmount,
                            author = "",
                            author1 = "",
                            author2 = "",
                            comment = "",
                            comment1 = "",
                            comment2 = "",
                            isLike = it.like != null,
                            isFavorite = it.favorite != null,
                            visibleLike = false,
                            visibleComment = false,
                            contents = it.review.contents,
                            reviewImages = it.images.stream().map { it.pictureUrl }.toList(),
                            restaurantId = it.review.restaurantId
                        )
                    }.toList()
                }

            override suspend fun addLike(reviewId: Int) {
                feedRepository.addLike(reviewId)
            }

            override suspend fun deleteLike(reviewId: Int) {
                feedRepository.deleteLike(reviewId)
            }

            override suspend fun deleteFavorite(reviewId: Int) {
                feedRepository.deleteFavorite(reviewId)
            }

            override suspend fun addFavorite(reviewId: Int) {
                feedRepository.addFavorite(reviewId)
            }

            override suspend fun getComment(reviewId: Int): List<CommentData> {
                return feedRepository.getComment(reviewId).stream().map {
                    it.toCommentData()
                }.toList()
            }

            override suspend fun addComment(reviewId: Int, comment: String) {
                feedRepository.addComment(reviewId, comment)
            }
        }
    }
}

fun RemoteComment.toCommentData(): CommentData {
    return CommentData(
        userId = this.user_id,
        profileImageUrl = this.profile_pic_url,
        date = this.create_date,
        comment = this.comment,
        name = this.user_name,
        likeCount = 0
    )
}

fun FeedEntity.toFeedTopUiState(): FeedTopUIState {
    return FeedTopUIState(
        reviewId = this.reviewId,
        name = this.userName,
        profilePictureUrl = this.profilePicUrl,
        rating = this.rating,
        restaurantName = this.restaurantName,
        userId = this.userId,
        restaurantId = this.restaurantId
    )
}

fun FeedEntity.toFeedBottomUiState(): FeedBottomUIState {
    return FeedBottomUIState(
        reviewId = this.reviewId,
        likeAmount = this.likeAmount,
        commentAmount = this.commentAmount,
        author = "",
        author1 = "",
        author2 = "",
        comment = "",
        comment1 = "",
        comment2 = "",
        isLike = false,
        isFavorite = false,
        visibleLike = true,
        visibleComment = true,
        contents = this.contents
    )
}

fun RemoteFeed.toFeedBottomUiState(): FeedBottomUIState {
    return FeedBottomUIState(
        reviewId = this.reviewId,
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
        visibleLike = true,
        visibleComment = true,
        contents = this.contents
    )
}

fun RemoteFeed.toFeedTopUiState(): FeedTopUIState {
    return FeedTopUIState(
        reviewId = this.reviewId,
        name = this.user.userName,
        profilePictureUrl = this.user.profilePicUrl,
        rating = this.rating,
        restaurantName = this.restaurant.restaurantName,
        userId = this.user.userId,
        restaurantId = this.restaurant.restaurantId
    )
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

@Composable
fun FeedScreen(
    feedsViewModel: FeedsViewModel = hiltViewModel(),
    clickAddReview: (() -> Unit),
    profileImageServerUrl: String,
    onProfile: ((Int) -> Unit),
    onImage: ((Int) -> Unit),
    onName: (() -> Unit),
    onRestaurant: ((Int) -> Unit),
    imageServerUrl: String
) {
    val uiState by feedsViewModel.uiState.collectAsState()

    Box {
        _FeedsScreen(
            feedsViewModel = feedsViewModel,
            feeds = {
                Feeds(
                    list = ArrayList(uiState.list.stream().map { it.toFeedUiState() }.toList()),
                    onProfile = onProfile,
                    onMenu = { feedsViewModel.onMenu() },
                    onImage = onImage,
                    onName = onName,
                    onLike = { feedsViewModel.onLike(it) },
                    onComment = { feedsViewModel.onComment(it) },
                    onShare = { feedsViewModel.onShare() },
                    onFavorite = { feedsViewModel.onFavorite(it) },
                    onRestaurant = onRestaurant,
                    profileImageServerUrl = profileImageServerUrl,
                    imageServerUrl = imageServerUrl,
                    isRefreshing = uiState.isRefreshing,
                    onRefresh = { feedsViewModel.refreshFeed() },
                )
            },
            torangToolbar = { TorangToolbar { clickAddReview.invoke() } },
            feedMenuBottomSheetDialog = {
                FeedMenuBottomSheetDialog(
                    isExpand = it,
                    onSelect = {},
                    onClose = { feedsViewModel.closeMenu() })
            },
            commentBottomSheetDialog = {
                CommentBottomSheetDialog(
                    isExpand = it,
                    onSelect = {},
                    onClose = { feedsViewModel.closeComment() },
                    list = uiState.comments?.stream()?.map { it.toCommentItemUiState() }?.toList()
                        ?: ArrayList(),
                    onSend = { feedsViewModel.sendComment(it) },
                    profileImageUrl = "",
                    profileImageServerUrl = profileImageServerUrl
                )
            },
            shareBottomSheetDialog = {
                ShareBottomSheetDialog(
                    isExpand = true,
                    onSelect = {},
                    onClose = { feedsViewModel.closeShare() })
            },
            errorComponent = {}, networkError = {}, loading = {}, emptyFeed = {}
        )
    }
}

fun CommentData.toCommentItemUiState(): CommentItemUiState {
    return CommentItemUiState(
        userId = userId,
        profileImageUrl = profileImageUrl,
        date = date,
        comment = comment,
        name = name,
        likeCount = likeCount
    )
}