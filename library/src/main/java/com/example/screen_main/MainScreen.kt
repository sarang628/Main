package com.example.screen_main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.screen_feed.FeedsScreen
import com.example.screen_feed.uistate.FeedsScreenUiState

@Composable
fun MainScreen(uiState: FeedsScreenUiState) {
    val navController = rememberNavController()
    Column {
        NavHost(
            navController = navController, startDestination = "profile",
            modifier = Modifier.weight(1f)
        ) {
            composable("profile") {
                FeedsScreen(uiState = uiState)
            }
            composable("friendslist") {
                Text(text = "friendslist")
            }
        }
        BottomNavigationComponent(navController = navController)
    }
}