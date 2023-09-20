package com.example.screen_main

import RestaurantScreen
import SettingsScreen
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.screen_feed.FeedsViewModel
import com.sarang.profile.ProfileScreen
import com.sarang.profile.viewmodel.ProfileViewModel
import com.sarang.screen_splash.SplashScreen
import com.sarang.toringlogin.login.LoginScreen
import com.sarang.toringlogin.login.LoginViewModel
import com.sryang.library.SelectPictureAndAddReview
import com.sryang.torang_repository.api.ApiReview
import com.sryang.torang_repository.session.SessionService
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

@Composable
fun TorangScreen(
    lifecycleOwner: LifecycleOwner,
    feedsViewModel: FeedsViewModel,
    remoteReviewService: ApiReview,
    loginViewModel: LoginViewModel,
    profileViewModel: ProfileViewModel,
    profileUrl: String
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
                    feedsViewModel = feedsViewModel
                )
            }
            composable("addReview") {
                AddReview(remoteReviewService) { navController.navigate("main") }
            }
            composable("restaurant") {
                RestaurantScreen()
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
                        if (SessionService(context).isLogin.value)
                            navController.navigate("main")
                        else
                            navController.navigate("login")
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
fun AddReview(remoteReviewService: ApiReview, onUploaded: () -> Unit) {
    var isProgress by remember { mutableStateOf(false) }
    val coroutine = rememberCoroutineScope()
    Box {
        SelectPictureAndAddReview(
            color = 0xFFFFFBE6,
            onShare = {
                //TODO viewmodel로 옮겨야 함.
                coroutine.launch {
                    try {
                        isProgress = true
                        remoteReviewService.addReview(
                            params = HashMap<String, RequestBody>().apply {
                                put(
                                    "contents",
                                    it.contents.toRequestBody("text/plain".toMediaTypeOrNull())
                                )
                            },
                            file = filesToMultipart(it.pictures)
                        )
                        isProgress = false
                        onUploaded.invoke()
                    } catch (e: Exception) {
                        Log.d("MainActivity", e.toString())
                        isProgress = false
                    }
                }
            })

        if (isProgress) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
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