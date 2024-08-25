package com.sarang.torang.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(
    feedScreen: @Composable () -> Unit,
    findingScreen: @Composable () -> Unit,
    myProfileScreen: @Composable () -> Unit,
    alarm: @Composable () -> Unit,
    onBottomMenu: ((String) -> Unit)? = null,
) {
    Column {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = "feed",
            modifier = Modifier.weight(1f)
        ) {
            composable("feed") { feedScreen.invoke() }
            composable("profile") { myProfileScreen.invoke() }
            composable("finding") { findingScreen.invoke() }
            composable("alarm") { alarm.invoke() }
        }
//        MainBottomNavigation(navController = navController, onBottomMenu = onBottomMenu)
        MainBottomNavigationAppBar(navController = navController, onBottomMenu = onBottomMenu)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen(
        feedScreen = { /*TODO*/ },
        findingScreen = { /*TODO*/ },
        myProfileScreen = { /*TODO*/ },
        alarm = { /*TODO*/ })
}