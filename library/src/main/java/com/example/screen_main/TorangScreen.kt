package com.example.screen_main

import RestaurantScreen
import SettingsScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.instagralleryModule.GalleryScreen
import com.sarang.profile.edit.EditProfileScreen
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
import restaurant_information.RestaurantInfoViewModel

@Composable
fun TorangScreen(
    lifecycleOwner: LifecycleOwner,
    addReviewViewModel: AddReviewViewModel,
    loginViewModel: LoginViewModel,
    profileViewModel: ProfileViewModel,
    restaurantInfoViewModel: RestaurantInfoViewModel,
    selectRestaurantViewModel: SelectRestaurantViewModel,
    feedScreen: @Composable () -> Unit,
    navController: NavHostController,
    findingScreen: @Composable () -> Unit,
    profileScreen: @Composable (NavBackStackEntry) -> Unit,
) {
    val context = LocalContext.current
    Column {
        NavHost(
            navController = navController, startDestination = "splash",
            modifier = Modifier.weight(1f)
        ) {
            composable("main") {
                MainScreen(
                    context = context,
                    lifecycleOwner = lifecycleOwner,
                    feedScreen = { feedScreen.invoke() },
                    findingScreen = { findingScreen.invoke() },
                    profileScreen = { profileScreen.invoke(it) }
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

            composable("profile/{id}") {
                profileScreen.invoke(it)
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
            composable("editProfile") {
                EditProfileScreen(onEditImage = { /*TODO*/ }) {
                    navController.popBackStack()
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