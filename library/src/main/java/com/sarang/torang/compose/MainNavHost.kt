package com.sarang.torang.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.sarang.torang.navigation.Feed
import com.sarang.torang.navigation.alarmScreen
import com.sarang.torang.navigation.feedGridScreen
import com.sarang.torang.navigation.feedScreen
import com.sarang.torang.navigation.findScreen
import com.sarang.torang.navigation.profileScreen

@Composable
fun MainNavHost(
    padding             : PaddingValues,
    state               : MainScreenState                               = rememberMainScreenState(),
    feed                : @Composable (onChat: () -> Unit) -> Unit      = {},
    feedGrid            : @Composable () -> Unit                        = {},
    profile             : @Composable () -> Unit                        = {},
    alarm               : @Composable () -> Unit                        = {},
    find                : @Composable () -> Unit                        = {},
) {
    NavHost(
        navController       = state.navController,
        startDestination    = Feed,
        modifier            = Modifier.fillMaxSize()
    )
    {
        feedScreen(padding, feed, state)
        feedGridScreen(padding, feedGrid)
        profileScreen(padding, profile)
        findScreen(padding, find)
        alarmScreen(padding, alarm)
    }
}