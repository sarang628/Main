package com.sarang.torang.compose

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

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

/**
 * @param onBottomMenu 하단 메뉴 선택 시 이벤트
 */
@Composable
fun MainBottomNavigation(navController: NavController, onBottomMenu: ((String) -> Unit)? = null) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var state by remember { mutableStateOf(0) }
    val titles = listOf("feed", "finding", "alarm", "profile")
    Column {
        PrimaryTabRow(
            selectedTabIndex = if (currentDestination == null) 0 else titles.indexOf(
                currentDestination.route.toString()
            )
        ) {
            items.forEachIndexed { index, screen ->
                Tab(
                    selected = currentDestination?.hierarchy?.any { it.route == titles[index] } == true,
                    onClick = {
                        Log.d("__MainBottomNavigation", "onClick ${titles[index]}")
                        if (onBottomMenu == null)
                            Log.w("__MainBottomNavigation", "onBottomMenu isn't set")

                        onBottomMenu?.invoke(titles[index])

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

@Preview
@Composable
fun MainBottomNavigationPreview() {
    MainBottomNavigation(navController = rememberNavController())
}

/**
 * @param onBottomMenu 하단 메뉴 선택 시 이벤트
 */
@Composable
fun MainBottomNavigationAppBar(navController: NavController, onBottomMenu: ((String) -> Unit)? = null) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var state by remember { mutableStateOf(0) }
    val titles = listOf("feed", "finding", "alarm", "profile")
    Column {
        NavigationBar(
            Modifier.height(50.dp),
            containerColor = MaterialTheme.colorScheme.background,
//            selectedTabIndex = if (currentDestination == null) 0 else titles.indexOf(
//                currentDestination.route.toString()
//            )
        ) {
            items.forEachIndexed { index, screen ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == titles[index] } == true,
                    onClick = {
                        Log.d("__MainBottomNavigation", "onClick ${titles[index]}")
                        if (onBottomMenu == null)
                            Log.w("__MainBottomNavigation", "onBottomMenu isn't set")

                        onBottomMenu?.invoke(titles[index])

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

@Preview
@Composable
fun MainBottomAppBarPreview() {
    MainBottomNavigationAppBar(navController = rememberNavController())
}