package com.sryang.torang.di.feed

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.sryang.main.BuildConfig.PROFILE_IMAGE_SERVER_URL
import com.sryang.main.BuildConfig.REVIEW_IMAGE_SERVER_URL

@Composable
fun ProvideFeedScreen(navController: NavHostController) {
    FeedScreen(
        onProfile = { navController.navigate("profile/$it") },
        onRestaurant = { navController.navigate("restaurant/$it") },
        onImage = {},
        onName = {},
        clickAddReview = { navController.navigate("addReview") },
        profileImageServerUrl = PROFILE_IMAGE_SERVER_URL,
        imageServerUrl = REVIEW_IMAGE_SERVER_URL,
        ratingBar = {},
        onComment = {},
        onMenu = {},
        onShare = {},
        onReport = {},
        onReported = {}
    )
}