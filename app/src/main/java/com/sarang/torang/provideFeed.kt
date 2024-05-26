package com.sarang.torang

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.sarang.torang.compose.feed.Feed
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.di.feed_di.toReview
import com.sarang.torang.di.image.provideTorangAsyncImage

fun provideFeed(
    onComment: ((Int) -> Unit),
    onMenu: ((Int) -> Unit),
    onShare: ((Int) -> Unit),
    navController: NavHostController,
    rootNavController: RootNavController,
): @Composable (Feed) -> Unit = {
    Feed(
        review = it.toReview(),
        isZooming = { /*scrollEnabled = !it*/ },
        progressTintColor = Color(0xFF000000),
        image = provideTorangAsyncImage(),
        onComment = { onComment.invoke(it.reviewId) },
        onShare = { onShare.invoke(it.reviewId) },
        onMenu = { onMenu.invoke(it.reviewId) },
        onName = { navController.navigate("profile/${it.userId}") },
        onRestaurant = { rootNavController.restaurant(it.restaurantId) },
        onImage = { },
        onProfile = { navController.navigate("profile/${it.userId}") }
    )
}