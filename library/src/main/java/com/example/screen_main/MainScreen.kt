package com.example.screen_main

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sarang.alarm.fragment.test
import com.sarang.alarm.uistate.testAlarmUiState
import com.sryang.torang_repository.session.SessionService

@Composable
fun MainScreen(
    feedScreen: @Composable () -> Unit,
    findingScreen: @Composable () -> Unit,
    profileScreen: @Composable (NavBackStackEntry) -> Unit,
) {
    Column {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = "feed",
            modifier = Modifier.weight(1f)
        ) {
            composable("feed") { feedScreen.invoke() }
            composable("profile",
                arguments = listOf(navArgument("id") { defaultValue = "0" })
            ) {
                profileScreen.invoke(it)
            }
            composable("finding") { findingScreen.invoke() }
            composable("alarm") { }
        }
        BottomNavigationComponent(navController = navController)
    }
}


