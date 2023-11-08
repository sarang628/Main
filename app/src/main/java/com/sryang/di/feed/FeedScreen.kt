package com.sryang.di.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.base_feed.ui.Feeds
import com.sarang.base_feed.ui.TorangToolbar
import com.sryang.library.CommentBottomSheetDialog
import com.sryang.library.FeedMenuBottomSheetDialog
import com.sryang.library.ShareBottomSheetDialog
import com.sryang.main.BuildConfig
import com.sryang.torang.compose._FeedsScreen
import com.sryang.torang.viewmodels.FeedsViewModel

@Composable
fun FeedScreen(
    feedsViewModel: FeedsViewModel = hiltViewModel(),
    clickAddReview: (() -> Unit),
    profileImageServerUrl: String = BuildConfig.PROFILE_IMAGE_SERVER_URL,
    onProfile: ((Int) -> Unit),
    onImage: ((Int) -> Unit),
    onName: (() -> Unit),
    onRestaurant: ((Int) -> Unit),
    imageServerUrl: String = BuildConfig.REVIEW_IMAGE_SERVER_URL,
    ratingBar: @Composable (Float) -> Unit
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
                    ratingBar = {}
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
                    profileImageUrl = uiState.myProfileUrl ?: "",
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