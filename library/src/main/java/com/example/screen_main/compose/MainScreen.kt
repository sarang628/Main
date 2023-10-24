package com.example.screen_main.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sryang.torang_repository.data.RemoteAlarm

@Composable
fun MainScreen(
    feedScreen: @Composable () -> Unit,
    findingScreen: @Composable () -> Unit,
    myProfileScreen: @Composable () -> Unit,
    alarm: @Composable () -> Unit,
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
            composable("alarm") { alarm.invoke() }
        }
        MainBottomNavigation(navController = navController)
    }
}


