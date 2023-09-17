package com.example.screen_main.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector) {
    object Feed : Screen("feed", Icons.Filled.Home)
    object Map : Screen("finding", Icons.Filled.AddCircle)
    object Alarm : Screen("alarm", Icons.Filled.Settings)
    object Profile : Screen("profile", Icons.Filled.Settings)
}


val items = listOf(
    Screen.Feed,
    Screen.Map,
    Screen.Alarm,
    Screen.Profile
)