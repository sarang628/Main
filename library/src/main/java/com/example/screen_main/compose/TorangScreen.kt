package com.example.screen_main.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun TorangScreen(
    navController: NavHostController,
    profileScreen: @Composable (NavBackStackEntry) -> Unit,
    settings: @Composable () -> Unit,
    splashScreen: @Composable () -> Unit,
    addReviewScreen: @Composable () -> Unit,
    loginScreen: @Composable () -> Unit,
    restaurantScreen: @Composable (NavBackStackEntry) -> Unit,
    editProfileScreen: @Composable () -> Unit,
    editProfileImageScreen: @Composable () -> Unit,
    mainScreen: @Composable () -> Unit
) {
    NavHost(
        navController = navController, startDestination = "splash",
    ) {
        composable("main") {
            mainScreen.invoke()
        }
        composable("addReview") {
            addReviewScreen.invoke()
        }
        composable("restaurant/{restaurantId}") { backStackEntry ->
            restaurantScreen.invoke(backStackEntry)
        }
        composable("profile/{id}") {
            profileScreen.invoke(it)
        }
        composable("splash") {
            splashScreen.invoke()
        }
        composable("login") {
            loginScreen.invoke()
        }
        composable("settings") {
            settings.invoke()
        }
        composable("editProfile") {
            editProfileScreen.invoke()
        }
        composable("EditProfileImage") {
            editProfileImageScreen.invoke()
        }
    }
}