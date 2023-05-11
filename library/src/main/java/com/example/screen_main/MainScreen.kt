package com.example.screen_main

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.screen_feed.FeedsScreen
import com.example.screen_feed.FeedsViewModel
import com.example.screen_feed.uistate.FeedsScreenUiState
import com.sarang.profile.ProfileScreen
import com.sarang.profile.uistate.ProfileUiState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainScreen(
    feedUiState: StateFlow<FeedsScreenUiState>,
    profileUiState: StateFlow<ProfileUiState>,
    context : Context
) {
    val navController = rememberNavController()
    Column {
        NavHost(
            navController = navController, startDestination = "profile",
            modifier = Modifier.weight(1f)
        ) {
            composable("profile") {
                val u by feedUiState.collectAsState()
                FeedsScreen(feedsViewModel = FeedsViewModel(context))
            }
            composable("friendslist") {
                val p by profileUiState.collectAsState()
                ProfileScreen(uiState = p)
            }
        }
        BottomNavigationComponent(navController = navController)
    }
}

@Composable
fun BottomNavigationComponent(navController: NavController) {
    var selectedIndex by remember { mutableStateOf(0) }
    val tabContents = listOf(
        "Feed" to Icons.Filled.Home,
        "Map" to Icons.Filled.AddCircle,
        "Alarm" to Icons.Filled.Settings,
        "Profile" to Icons.Filled.Settings
    )

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.colorSecondaryLight),
        contentColor = MaterialTheme.colors.onSurface,
        elevation = 2.dp
    ) {
        tabContents.forEachIndexed { index, pair: Pair<String, ImageVector> ->
            BottomNavigationItem(
                icon = { Icon(pair.second, contentDescription = null) },
                label = { androidx.compose.material.Text(pair.first) },
                selected = selectedIndex == index,
                alwaysShowLabel = false, // Hides the title for the unselected items
                onClick = {
                    selectedIndex = index
                    if (index == 0) {
                        navController.navigate("profile")
                    } else {
                        navController.navigate("friendslist")
                    }
                }
            )
        }
    }
}