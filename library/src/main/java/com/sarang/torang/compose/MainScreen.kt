package com.sarang.torang.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(
    feedScreen: @Composable (onComment: ((Int) -> Unit), onMenu: ((Int) -> Unit), onShare: ((Int) -> Unit), navBackStackEntry: NavBackStackEntry) -> Unit,
    findingScreen: @Composable () -> Unit,
    myProfileScreen: @Composable () -> Unit,
    alarm: @Composable () -> Unit,
    onBottomMenu: ((String) -> Unit)? = null,
    onComment: (Int) -> Unit,
    onMenu: (Int) -> Unit,
    onShare: (Int) -> Unit,
) {
    Column {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = "feed",
            modifier = Modifier.weight(1f)
        ) {
            composable("feed") { navBackStackEntry ->
                feedScreen.invoke(onComment, onMenu, onShare, navBackStackEntry)
            }
            composable("profile") { myProfileScreen.invoke() }
            composable("finding") { findingScreen.invoke() }
            composable("alarm") { alarm.invoke() }
        }
        MainBottomNavigation(navController = navController, onBottomMenu = onBottomMenu)
    }
}