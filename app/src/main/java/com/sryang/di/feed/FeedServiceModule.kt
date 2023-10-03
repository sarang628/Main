package com.sryang.di.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.screen_feed.FeedData
import com.example.screen_feed.FeedService
import com.example.screen_feed.FeedsScreen
import com.example.screen_feed.FeedsViewModel
import com.sarang.base_feed.ui.Feeds
import com.sarang.base_feed.ui.TorangToolbar
import com.sarang.base_feed.uistate.FeedBottomUIState
import com.sarang.base_feed.uistate.FeedTopUIState
import com.sarang.base_feed.uistate.FeedUiState
import com.sryang.library.CommentBottomSheetDialog
import com.sryang.library.FeedMenuBottomSheetDialog
import com.sryang.library.ShareBottomSheetDialog
import com.sryang.torang_repository.data.entity.FeedEntity
import com.sryang.torang_repository.data.remote.response.RemoteFeed
import com.sryang.torang_repository.repository.feed.FeedRepository
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
            override suspend fun getFeeds(params: Map<String, String>) {
                feedRepository.loadFeed()
            }

            override val feeds1: Flow<List<FeedData>>
                get() = feedRepository.feeds1.map {
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
                            isLike = false,
                            isFavorite = false,
                            visibleLike = false,
                            visibleComment = false,
                            contents = it.review.contents,
                            reviewImages = it.images.stream().map { it.pictureUrl }.toList(),
                            restaurantId = it.review.restaurantId
                        )
                    }.toList()
                }

            override suspend fun addLike(reviewId: Int) {
                TODO("Not yet implemented")
            }

            override suspend fun deleteLike(reviewId: Int) {
                TODO("Not yet implemented")
            }

            override suspend fun deleteFavorite(reviewId: Int) {
                TODO("Not yet implemented")
            }

            override suspend fun addFavorite(reviewId: Int) {
                TODO("Not yet implemented")
            }
        }
    }
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
        isLike = this.like?.isLike ?: false,
        isFavorite = this.favorite?.isFavority ?: false,
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
        restaurantId = this.restaurantId
    )
}

@Composable
fun TestFeedScreen(
    feedsViewModel: FeedsViewModel,
    onProfile: ((Int) -> Unit), // 프로필 이미지 클릭
    onRestaurant: ((Int) -> Unit), // 식당명 클릭
    onImage: ((Int) -> Unit), // 이미지 클릭
    onName: (() -> Unit), // 이름 클릭
    onAddReview: (() -> Unit), // 리뷰 추가 클릭
) {
    val context = LocalContext.current
    var isExpandMenuBottomSheet by remember { mutableStateOf(false) }
    var isExpandCommentBottomSheet by remember { mutableStateOf(false) }
    var isShareCommentBottomSheet by remember { mutableStateOf(false) }
    val uiState by feedsViewModel.uiState.collectAsState()

    Box {
        FeedsScreen(
            feedsViewModel = feedsViewModel,
            isExpandMenuBottomSheet = isExpandMenuBottomSheet,
            isExpandCommentBottomSheet = isExpandCommentBottomSheet,
            isShareCommentBottomSheet = isShareCommentBottomSheet,
            onReview = {},
            itemFeed = {
                Feeds(
                    list = ArrayList(uiState.list.stream().map { it.toFeedUiState() }.toList()),
                    onProfile = onProfile,
                    onMenu = { isExpandMenuBottomSheet = !isExpandMenuBottomSheet },
                    onImage = onImage,
                    onName = onName,
                    onLike = { },
                    onComment = { isExpandCommentBottomSheet = !isExpandCommentBottomSheet },
                    onShare = { isShareCommentBottomSheet = !isShareCommentBottomSheet },
                    onFavorite = { },
                    onRestaurant = onRestaurant,
                    profileImageServerUrl = "http://sarang628.iptime.org:89/profile_images/",
                    imageServerUrl = "http://sarang628.iptime.org:89/review_images/",
                    isRefreshing = uiState.isRefreshing,
                    onRefresh = { feedsViewModel.refreshFeed() }
                )
            },
            torangToolbar = { TorangToolbar({ onAddReview.invoke() }) },
            errorComponent = {
                Column {
//                            if (uiState.isEmptyFeed) {
                    // 피드가 비어있을 때
                    //EmptyFeed()
//                            }

//                            if (uiState.isVisibleRefreshButton()) {
                    // 네트워크 에러
                    //NetworkError()
//                            }
//                            if (uiState.isProgess) {
                    // 로딩
                    //Loading()
//                            }
                }
            },
            feedMenuBottomSheetDialog = {
                FeedMenuBottomSheetDialog(isExpand = it, onSelect = {})
            },
            shareBottomSheetDialog = {
                ShareBottomSheetDialog(isExpand = it, onSelect = {})
            },
            commentBottomSheetDialog = {
                CommentBottomSheetDialog(isExpand = it, onSelect = {})
            }
        )
    }
}