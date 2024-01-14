package com.sarang.torang.di.feed_di

import android.util.Log
import androidx.compose.runtime.Composable
import com.sryang.torang.compose.feed.FeedScreen
import com.sryang.torang.compose.feed.Feeds
import com.sryang.torang.uistate.FeedsUiState

@Composable
fun ProvideFeedScreen(
    onAddReview: (() -> Unit),
    ratingBar: @Composable (Float) -> Unit
) {
    val TAG = "_ProvideFeedScreen"
    FeedScreen(
        onAddReview = onAddReview,
    ) { list, onRefresh, onBottom, isRefreshing ->
        Feeds(
            onRefresh = onRefresh,
            onBottom = onBottom,
            ratingBar = { _, _ -> ratingBar },
            isRefreshing = isRefreshing,
            feedsUiState = FeedsUiState.Success(
                list.map {
                    it.review(
                        onProfile = { Log.d(TAG, "onProfile()") },
                        onImage = { Log.d(TAG, "onImage()") },
                        onRestaurant = { Log.d(TAG, "onRestaurant()") },
                        onName = { Log.d(TAG, "onName()") },
                        onMenu = { Log.d(TAG, "onMenu()") },
                        onShare = { Log.d(TAG, "onShare()") },
                        onComment = { Log.d(TAG, "onComment()") }
                    )
                }
            )
        )
    }
}
