package com.sarang.torang.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object FeedGrid // route to FeedGrid screen

fun NavController.navigateToFeedGrid(navOptions: NavOptions) = navigate(route = FeedGrid, navOptions)

fun NavGraphBuilder.feedGridScreen(
    padding: PaddingValues = PaddingValues(0.dp),
    feedGrid: @Composable () -> Unit = {},
) {
    composable<FeedGrid> {
        Box(Modifier
            .fillMaxSize()
            .padding(padding)) { feedGrid.invoke() }
    }
}