package com.sarang.torang.compose

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class Screen(val route: String, val icon: ImageVector) {
    object Feed : Screen("feed", Icons.Filled.Home)
    object Map : Screen("finding", Icons.Filled.Search)
    object Alarm : Screen("alarm", Icons.Filled.Notifications)
    object Profile : Screen("myProfile", Icons.Filled.Face)
}


val items = listOf(
    Screen.Feed,
    Screen.Map,
    Screen.Alarm,
    Screen.Profile
)

@Composable
fun MainBottomNavigation(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var state by remember { mutableStateOf(0) }
    val titles = listOf("feed", "finding", "alarm", "profile")
    Log.d("RestaurntTopMenu1", currentDestination?.route.toString())
    Column {
        PrimaryTabRow(
            selectedTabIndex = if (currentDestination == null) 0 else titles.indexOf(
                currentDestination.route.toString()
            ),
            indicator = {}
        ) {
            items.forEachIndexed { index, screen ->
                Tab(
                    selected = currentDestination?.hierarchy?.any { it.route == titles[index] } == true,
                    onClick = {
                        state = index
                        navController.navigate(titles[index]) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(imageVector = screen.icon, contentDescription = "")
                    }
                )
            }
        }
    }
}