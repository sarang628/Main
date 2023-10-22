package com.example.screen_main.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(
    feedScreen: @Composable () -> Unit,
    findingScreen: @Composable () -> Unit,
    myProfileScreen: @Composable () -> Unit,
) {
    Column {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = "feed",
            modifier = Modifier.weight(1f)
        ) {
            composable("feed") { feedScreen.invoke() }
            composable("myProfile") { myProfileScreen.invoke() }
            composable("finding") { findingScreen.invoke() }
            composable("alarm") { }
        }
        MainBottomNavigation(navController = navController)
    }
}


