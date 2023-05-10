package com.example.screen_main

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Preview
@Composable
fun NavigationTest() {
    val navController = rememberNavController()

    Column() {
        NavHost(
            navController = navController, startDestination = "profile",
            modifier = Modifier.weight(1f)
        ) {
            composable("profile") {
                Text(text = "profile")
            }
            composable("friendslist") {
                Text(text = "friendslist")
            }
            /*...*/
        }
        BottomNavigationComponent(navController = navController)
    }
}

@Composable
fun BottomNavigationComponent(navController: NavController) {
    var selectedIndex by remember { mutableStateOf(0) }
    val tabContents = listOf(
        "Home" to Icons.Filled.Home,
        "Map" to Icons.Filled.AddCircle,
        "Settings" to Icons.Filled.Settings
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface,
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