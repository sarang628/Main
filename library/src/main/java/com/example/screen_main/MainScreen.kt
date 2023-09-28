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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cardinfo.RestaurantCardViewModel
import com.example.screen_feed.FeedsViewModel
import com.example.screen_map.MapViewModel
import com.sarang.profile.ProfileScreen
import com.sarang.profile.viewmodel.ProfileViewModel
import com.sarang.screen_splash.SplashScreen
import com.sarang.toringlogin.login.LoginScreen
import com.sarang.toringlogin.login.LoginViewModel
import com.sryang.library.AddReviewScreen
import com.sryang.library.ReviewService
import com.sryang.torang_repository.session.SessionService
import kotlinx.coroutines.launch
import restaurant_information.RestaurantInfoViewModel

@Composable
fun TorangScreen(
    lifecycleOwner: LifecycleOwner,
    feedsViewModel: FeedsViewModel,
    remoteReviewService: ReviewService,
    loginViewModel: LoginViewModel,
    profileViewModel: ProfileViewModel,
    mapViewModel: MapViewModel,
    restaurantCardViewModel: RestaurantCardViewModel,
    profileUrl: String,
    restaurantInfoViewModel: RestaurantInfoViewModel
) {
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()
    val navController = rememberNavController()

    Column {
        NavHost(
            navController = navController, startDestination = "splash",
            modifier = Modifier.weight(1f)
        ) {
            composable("main") {
                MainScreen(
                    context = context,
                    lifecycleOwner = lifecycleOwner,
                    clickAddReview = { navController.navigate("addReview") },
                    onProfile = { navController.navigate("profile/$it") },
                    clickShare = { },
                    clickImage = { },
                    clickRestaurant = { navController.navigate("restaurant") },
                    navController1 = navController,
                    profileViewModel = profileViewModel,
                    feedsViewModel = feedsViewModel,
                    mapViewModel = mapViewModel,
                    restaurantCardViewModel = restaurantCardViewModel
                )
            }
            composable("addReview") {
                //AddReviewScreen(remoteReviewService = )
                AddReviewScreen(remoteReviewService)
            }
            composable("restaurant/{restaurantId}") {
                RestaurantScreen(
                    restaurantId = 10,
                    restaurantInfoViewModel = restaurantInfoViewModel
                )
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
                            navController.navigate("splash")
                        }
                    },
                    profileBaseUrl = profileUrl
                )
            }
            composable("splash") {
                Column {
                    SplashScreen(onSuccess = {
                        if (SessionService(context).isLogin.value) {
                            navController.navigate("main")
                        } else {
                            navController.navigate("login")
                        }
                    })
                }
            }
            composable("login") {
                val flow = loginViewModel.uiState.collectAsState()
                LoginScreen(isLogin = flow.value.isLogin, onLogin = {
                    loginViewModel.login(it)
                    navController.navigate("main")
                }, onLogout = {
                    loginViewModel.logout()
                })
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