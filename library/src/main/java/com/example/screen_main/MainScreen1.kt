package com.example.screen_main

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cardinfo.RestaurantCardViewModel
import com.example.screen_feed.FeedsScreen
import com.example.screen_feed.FeedsScreenInputEvents
import com.example.screen_feed.FeedsViewModel
import com.example.screen_finding.finding.TextFindScreen
import com.example.screen_map.MapViewModel
import com.sarang.alarm.fragment.test
import com.sarang.alarm.uistate.testAlarmUiState
import com.sarang.profile.ProfileScreen
import com.sarang.profile.viewmodel.ProfileViewModel
import com.sryang.torang_repository.session.SessionService
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    clickAddReview: ((Int) -> Unit),
    onProfile: ((Int) -> Unit),
    clickShare: ((Int) -> Unit),
    clickImage: ((Int) -> Unit),
    clickRestaurant: (() -> Unit),
    feedsViewModel: FeedsViewModel,
    profileViewModel: ProfileViewModel,
    mapViewModel: MapViewModel,
    restaurantCardViewModel: RestaurantCardViewModel,
    navController1: NavController
) {
    val alarmUiState = testAlarmUiState(context = context, lifecycleOwner)
    val sessionService = SessionService(LocalContext.current)
    var isExpandMenuBottomSheet by remember { mutableStateOf(false) }
    var isExpandCommentBottomSheet by remember { mutableStateOf(false) }
    var isShareCommentBottomSheet by remember { mutableStateOf(false) }
    val coroutine = rememberCoroutineScope()
    Column {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = "feed",
            modifier = Modifier.weight(1f)
        ) {
            composable("feed") {
                FeedsScreen(
                    uiStateFlow = feedsViewModel.uiState,
                    inputEvents = FeedsScreenInputEvents(
                        onRefresh = {
                            feedsViewModel.refreshFeed()
                        },
                        onProfile = onProfile,
                        onAddReview = clickAddReview,
                        onImage = clickImage,
                        onRestaurant = clickRestaurant,
                        onMenu = {
                            isExpandMenuBottomSheet = !isExpandMenuBottomSheet
                        },
                        onFavorite = { feedsViewModel.clickFavorite(it) },
                        onShare = {
                            isShareCommentBottomSheet = !isShareCommentBottomSheet
                        },
                        onComment = {
                            isExpandCommentBottomSheet = !isExpandCommentBottomSheet
                        },
                        onLike = { feedsViewModel.clickLike(it) },
                        onName = { },
                    ),
                    imageServerUrl = "http://sarang628.iptime.org:89/review_images/",
                    profileImageServerUrl = "http://sarang628.iptime.org:89/profile_images/",
                    isExpandMenuBottomSheet = isExpandMenuBottomSheet,
                    isExpandCommentBottomSheet = isExpandCommentBottomSheet,
                    isShareCommentBottomSheet = isShareCommentBottomSheet,
                    onBottom = {}
                )
            }
            composable("profile") {
                ProfileScreen(profileViewModel = profileViewModel, onLogout = {
                    coroutine.launch {
                        sessionService.removeToken()
                        navController1.navigate("splash")
                    }
                })
            }
            composable("finding") {
                TextFindScreen(
                    mapViewModel = mapViewModel,
                    restaurantVardViewModel = restaurantCardViewModel,
                    restaurantImageUrl = "http://sarang628.iptime.org:89/restaurant_images/"
                )
            }
            composable("alarm") {
                test(alarmUiState)
            }
        }
        BottomNavigationComponent(navController = navController)

    }
}