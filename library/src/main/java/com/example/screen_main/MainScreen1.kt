package com.example.screen_main

import android.content.Context
import android.util.Log
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cardinfo.RestaurantCardPage
import com.example.cardinfo.RestaurantCardViewModel
import com.example.screen_finding.finding.FindScreen
import com.example.screen_map.MapScreen
import com.example.screen_map.MapViewModel
import com.sarang.alarm.fragment.test
import com.sarang.alarm.uistate.testAlarmUiState
import com.sarang.base_feed.ui.Feeds
import com.sarang.base_feed.uistate.FeedUiState
import com.sarang.base_feed.uistate.testFeedUiState
import com.sarang.profile.ProfileScreen
import com.sarang.profile.move
import com.sarang.profile.viewmodel.ProfileViewModel
import com.sryang.torang_repository.session.SessionService
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    profileViewModel: ProfileViewModel,
    mapViewModel: MapViewModel,
    restaurantCardViewModel: RestaurantCardViewModel,
    navController1: NavHostController,
    feedScreen: @Composable () -> Unit
) {
    val alarmUiState = testAlarmUiState(context = context, lifecycleOwner)
    val sessionService = SessionService(LocalContext.current)
    val coroutine = rememberCoroutineScope()
    Column {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = "feed",
            modifier = Modifier.weight(1f)
        ) {
            composable("feed") {
                feedScreen.invoke()
            }
            composable("profile") {
                ProfileScreen(profileViewModel = profileViewModel, onLogout = {
                    coroutine.launch {
                        sessionService.removeToken()
                        navController1.move("splash")
                    }
                }, favorite = {
                    Feeds(
                        list = ArrayList<FeedUiState>().apply {
                            add(testFeedUiState())
                            add(testFeedUiState())
                            add(testFeedUiState())
                            add(testFeedUiState())
                            add(testFeedUiState())
                            add(testFeedUiState())
                        },
                        onProfile = {},
                        onLike = {},
                        onComment = {},
                        onShare = {},
                        onFavorite = {},
                        onMenu = { /*TODO*/ },
                        onName = { /*TODO*/ },
                        onRestaurant = { /*TODO*/ },
                        onImage = {},
                        onRefresh = { /*TODO*/ },
                        isRefreshing = false
                    )
                }, wantToGo = {
                    Feeds(
                        list = ArrayList<FeedUiState>().apply {
                            add(testFeedUiState())
                            add(testFeedUiState())
                            add(testFeedUiState())
                            add(testFeedUiState())
                            add(testFeedUiState())
                            add(testFeedUiState())
                        },
                        onProfile = {},
                        onLike = {},
                        onComment = {},
                        onShare = {},
                        onFavorite = {},
                        onMenu = { /*TODO*/ },
                        onName = { /*TODO*/ },
                        onRestaurant = { /*TODO*/ },
                        onImage = {},
                        onRefresh = { /*TODO*/ },
                        isRefreshing = false
                    )
                })
            }
            composable("finding") {
                //remember로 설정하지 않으면 다른화면으로 갔다 돌아왔을 때 동작하지 않음.
                var isMovingByMarkerClick by remember { mutableStateOf(false) }
                FindScreen(
                    restaurantCardPage = {
                        RestaurantCardPage(
                            uiState = restaurantCardViewModel.uiState,
                            restaurantImageUrl = "http://sarang628.iptime.org:89/restaurant_images/",
                            onChangePage = { page ->
                                Log.d("MainActivity", "onPageChange : $it")
                                restaurantCardViewModel.uiState.value.restaurants?.let { restaurants ->
                                    val restaurantId = restaurants[page].restaurantId
                                    restaurantCardViewModel.selectRestaurant(restaurantId)

                                    Log.d(
                                        "MainScreen",
                                        "isMovingByMarkerClick : $isMovingByMarkerClick"
                                    )
                                    if (!isMovingByMarkerClick) {
                                        mapViewModel.selectRestaurantById(id = restaurantId)
                                    }
                                }
                            }, onClickCard = { navController1.navigate("restaurant/$it") }
                        )
                    },
                    mapScreen = {
                        MapScreen(
                            mapViewModel = mapViewModel,
                            mapViewModel.mapUiStateFlow,
                            onMark = {
                                Log.d("MainScreen", "onMark : $it")
                                isMovingByMarkerClick = true
                                restaurantCardViewModel.selectRestaurant(it)
                            },
                            animationMoveDuration = 300,
                            onIdle = {
                                isMovingByMarkerClick = false
                            }
                        )
                    }
                )
            }
            composable("alarm") {
                test(alarmUiState)
            }
        }
        BottomNavigationComponent(navController = navController)

    }
}
