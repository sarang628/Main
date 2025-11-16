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
    state               : MainScreenState                               = rememberMainScreenState()
) {
    NavHost(
        navController       = state.navController,
        startDestination    = Feed,
        modifier            = Modifier.fillMaxSize()
    ) {
        feedScreen(padding, state)
        feedGridScreen(padding)
        profileScreen(padding)
        findScreen(padding)
        alarmScreen(padding)
    }
}