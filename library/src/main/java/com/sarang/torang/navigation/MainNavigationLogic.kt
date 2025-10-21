package com.sarang.torang.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.navOptions
import com.sarang.torang.compose.main.MainDestination
import kotlinx.coroutines.flow.StateFlow

fun mainNavigationLogic(
    navController   : NavController,
    destination     : MainDestination = MainDestination.FEED,
    onAddReview     : () -> Unit      = { },
    onBottomMenu    : (MainDestination) -> Unit   = { },
){
    if (destination == MainDestination.ADD) {
        onAddReview.invoke()
        return
    }

    val topLevelNavOptions = navOptions {
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

    when (destination){
        MainDestination.FEED        -> navController.navigateToFeed(topLevelNavOptions)
        MainDestination.FEED_GRID   -> navController.navigateToFeedGrid(topLevelNavOptions)
        MainDestination.ADD         -> {}
        MainDestination.FIND        -> navController.navigateToFind(topLevelNavOptions)
        MainDestination.PROFILE     -> navController.navigateToProfile(topLevelNavOptions)
    }
    onBottomMenu.invoke(destination)
}


val NavController.currentDestination: NavDestination?
    @Composable get() {
        val previousDestination = remember { mutableStateOf<NavDestination?>(null) }
        // Collect the currentBackStackEntryFlow as a state
        val currentEntry = this.currentBackStackEntryFlow
            .collectAsState(initial = null)

        // Fallback to previousDestination if currentEntry is null
        return currentEntry.value?.destination.also { destination ->
            if (destination != null) {
                previousDestination.value = destination
            }
        } ?: previousDestination.value
    }