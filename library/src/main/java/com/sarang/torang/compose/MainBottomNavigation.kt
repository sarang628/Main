package com.sarang.torang.compose

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.main.icon
import com.sarang.torang.compose.main.items

/**
 * @param onBottomMenu 하단 메뉴 선택 시 이벤트
 */
@Composable
fun MainBottomNavigation(navController: NavController, onBottomMenu: ((String) -> Unit)? = null) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
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