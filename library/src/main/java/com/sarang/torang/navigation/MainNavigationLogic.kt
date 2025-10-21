package com.sarang.torang.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.navOptions
import com.sarang.torang.compose.main.MainDestination

fun mainNavigationLogic(
    navController   : NavController,
    destination     : MainDestination = MainDestination.FEED,
){
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
}