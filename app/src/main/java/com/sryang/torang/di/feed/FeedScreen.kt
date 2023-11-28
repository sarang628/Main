package com.sryang.torang.di.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sryang.base.feed.compose.feed.Feeds
import com.sryang.main.BuildConfig
import com.sryang.torang.R
import com.sryang.torang.uistate.FeedUiState
import com.sryang.torang.uistate.isEmpty
import com.sryang.torang.viewmodels.FeedsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    profileImageServerUrl: String = BuildConfig.PROFILE_IMAGE_SERVER_URL,
    imageServerUrl: String = BuildConfig.REVIEW_IMAGE_SERVER_URL,
    feedsViewModel: FeedsViewModel = hiltViewModel(),
    clickAddReview: (() -> Unit),
    onProfile: ((Int) -> Unit),
    onImage: ((Int) -> Unit),
    onName: ((Int) -> Unit),
    onRestaurant: ((Int) -> Unit),
    onComment: ((Int) -> Unit),
    onShare: ((Int) -> Unit),
    onMenu: ((Int) -> Unit),
    onReport: ((Int) -> Unit),
    onReported: () -> Unit,
    ratingBar: @Composable (Float) -> Unit
) {
    val uiState: FeedUiState by feedsViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = uiState.error, block = {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
            feedsViewModel.clearErrorMsg()
        }
    })

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = { Text(text = "Torang", fontSize = 21.sp, fontWeight = FontWeight.Bold) },
                actions = {
                    Image(painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                clickAddReview.invoke()
                            })
                })
        }) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues))
        {
            Feeds(
                profileImageServerUrl = profileImageServerUrl,
                imageServerUrl = imageServerUrl,
                list = ArrayList(uiState.list.map { it.review() }),
                onLike = { feedsViewModel.onLike(it) },
                onFavorite = { feedsViewModel.onFavorite(it) },
                onRefresh = { feedsViewModel.refreshFeed() },
                onBottom = { feedsViewModel.onBottom() },
                onProfile = onProfile,
                onMenu = onMenu,
                onImage = onImage,
                onName = onName,
                onComment = onComment,
                onShare = onShare,
                onRestaurant = onRestaurant,
                ratingBar = ratingBar,
                isRefreshing = uiState.isRefreshing,
                isEmpty = uiState.isEmpty,
            )
        }
    }
}
