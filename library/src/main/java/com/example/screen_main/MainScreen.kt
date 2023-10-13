package com.example.screen_main

import RestaurantScreen
import SettingsScreen
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cardinfo.RestaurantCardViewModel
import com.example.screen_map.MapViewModel
import com.sarang.base_feed.ui.Feeds
import com.sarang.base_feed.uistate.FeedUiState
import com.sarang.base_feed.uistate.testFeedUiState
import com.sarang.instagralleryModule.GalleryScreen
import com.sarang.profile.ProfileScreen
import com.sarang.profile.move
import com.sarang.profile.viewmodel.ProfileViewModel
import com.sarang.screen_splash.SplashScreen
import com.sarang.toringlogin.login.LoginScreen
import com.sarang.toringlogin.login.LoginViewModel
import com.sryang.library.AddReviewScreen
import com.sryang.library.AddReviewViewModel
import com.sryang.library.go
import com.sryang.library.selectrestaurant.SelectRestaurantViewModel
import com.sryang.torang_repository.session.SessionService
import kotlinx.coroutines.launch
import restaurant_information.RestaurantInfoViewModel

@Composable
fun TorangScreen(
    lifecycleOwner: LifecycleOwner,
    addReviewViewModel: AddReviewViewModel,
    loginViewModel: LoginViewModel,
    profileViewModel: ProfileViewModel,
    mapViewModel: MapViewModel,
    restaurantCardViewModel: RestaurantCardViewModel,
    profileUrl: String,
    restaurantInfoViewModel: RestaurantInfoViewModel,
    selectRestaurantViewModel: SelectRestaurantViewModel,
    feedScreen: @Composable () -> Unit,
    navController: NavHostController,
    findingScreen: @Composable () -> Unit
) {
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()

    Column {
        NavHost(
            navController = navController, startDestination = "splash",
            modifier = Modifier.weight(1f)
        ) {
            composable("main") {
                MainScreen(
                    context = context,
                    lifecycleOwner = lifecycleOwner,
                    navController1 = navController,
                    profileViewModel = profileViewModel,
                    mapViewModel = mapViewModel,
                    restaurantCardViewModel = restaurantCardViewModel,
                    feedScreen = { feedScreen.invoke() },
                    findingScreen = { findingScreen.invoke() }
                )
            }
            composable("addReview") {
                val addReviewNavController = rememberNavController()
                AddReviewScreen(
                    addReviewViewModel = addReviewViewModel,
                    navController = addReviewNavController,
                    galleryScreen = {
                        GalleryScreen(color = 0xFFFFFBE6, onNext = {
                            addReviewViewModel.selectPictures(it)
                            addReviewNavController.navigate("addReview")
                        }, onClose = {
                            navController.popBackStack()
                        })
                    },
                    selectRestaurantViewModel = selectRestaurantViewModel,
                    onRestaurant = {
                        addReviewViewModel.selectRestaurant(it)
                        addReviewNavController.popBackStack()
                    },
                    onShared = {
                        navController.popBackStack()
                    }
                )
            }
            composable("restaurant/{restaurantId}") { backStackEntry ->
                val restaurantId = backStackEntry.arguments?.getString("restaurantId")
                restaurantId?.let {
                    RestaurantScreen(
                        restaurantId = it.toInt(),
                        restaurantInfoViewModel = restaurantInfoViewModel,
                        reviewImageUrl = "http://sarang628.iptime.org:89/review_images/",
                        restaurantImageUrl = "http://sarang628.iptime.org:89/restaurant_images/"
                    )
                }
            }

            composable(
                "profile/{userId}",
                arguments = listOf(navArgument("userId") { type = NavType.StringType })
            ) {
                val userId = it.arguments?.getString("userId")?.toInt() ?: 0
                Log.d("TorangScreen", "userId = $userId")
                profileViewModel.loadProfile(userId)
                ProfileScreen(
                    profileViewModel = profileViewModel, onLogout =
                    {
                        coroutine.launch {
                            SessionService(context).removeToken()
                            navController.move("splash")
                        }
                    },
                    profileBaseUrl = profileUrl,
                    favorite = {
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
                    },
                    wantToGo = {
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
                    }
                )
            }
            composable("splash") {
                Column {
                    SplashScreen(onSuccess = {
                        if (SessionService(context).isLogin.value) {
                            navController.move("main")
                        } else {
                            navController.move("login")
                        }
                    })
                }
            }
            composable("login") {
                val flow = loginViewModel.uiState.collectAsState()
                LoginScreen(
                    loginViewModel,
                    onLogin = { navController.go("main") },
                    onLogout = { navController.go("splash") }
                )
            }
            composable("settings") {
                SettingsScreen {

                }
            }
        }
    }
}

@Composable
@Preview
fun PreView() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationComponent(navController = navController) }
    ) { innerPaddingModifier ->
        Text(text = "", Modifier.padding(innerPaddingModifier))
        NavHost(navController = navController, startDestination = "profile") {
            composable("profile") {
                Text(text = "profile")
            }
            composable("finding") {
                Text(text = "finding")
            }
            composable("alarm") {
                Text(text = "alarm")
            }
            composable("friendslist") {
                Text(text = "friendslist")
            }
        }
    }
}