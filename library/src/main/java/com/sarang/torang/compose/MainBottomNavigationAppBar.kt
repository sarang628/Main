package com.sarang.torang.compose

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.main.Add
import com.sarang.torang.compose.main.FindingMap
import com.sarang.torang.compose.main.Feed
import com.sarang.torang.compose.main.FeedGrid
import com.sarang.torang.compose.main.Profile
import com.sarang.torang.compose.main.icon
import com.sarang.torang.compose.main.mainNavigations

/**
 * @param onBottomMenu 하단 메뉴 선택 시 이벤트
 */
@Composable
fun MainBottomNavigationAppBar(
    navController: NavController,
    onBottomMenu: ((Any) -> Unit)? = null,
    onAddReview: () -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    MainBottomNavigationAppBar(
        items = mainNavigations,
        route = currentDestination?.route,
        onBottomMenu = {
            if (it == Add) {
                onAddReview.invoke()
                return@MainBottomNavigationAppBar
            }

            navController.navigate(it) {
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
            onBottomMenu?.invoke(it)
        })

}

@Composable
private fun MainBottomNavigationAppBar(
    items: List<Any>,
    route: Any?,
    onBottomMenu: ((Any) -> Unit)? = null,
) {
    Column {
        NavigationBar(
            Modifier.height(50.dp),
            containerColor = MaterialTheme.colorScheme.background,
        ) {
            items.forEachIndexed { index, screen ->
                NavigationBarItem(
                    selected = route == screen.toString(),
                    onClick = {
                        Log.d("__MainBottomNavigation", "bottom menu click : ${screen}")
                        if (onBottomMenu == null)
                            Log.w("__MainBottomNavigation", "onBottomMenu isn't set")

                        onBottomMenu?.invoke(screen)
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

    val navController = rememberNavController()
    var currentDestination: Any by remember { mutableStateOf(Feed) }


    Box {
        NavHost(navController = navController, startDestination = currentDestination) {
            composable<Feed> {}
            composable<FeedGrid> {}
            composable<FindingMap> {}
            composable<Profile> {}
            composable<Add> {}
        }
    }

    MainBottomNavigationAppBar(
        items = mainNavigations,
        route = currentDestination,
        onBottomMenu = {
            Log.d("__Preview", it.toString())
            currentDestination = it
        }
    )
}