package com.sarang.torang.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sarang.torang.compose.MainScreenState
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data object Feed // route to Feed screen

fun NavController.navigateToFeed(navOptions: NavOptions) = navigate(route = Feed, navOptions)

fun NavGraphBuilder.feedScreen(
    padding : PaddingValues                             = PaddingValues(0.dp),
    feed    : @Composable (onChat: () -> Unit) -> Unit  = {},
    state   : MainScreenState
) {
    composable<Feed> {
        val coroutineScope = rememberCoroutineScope()
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.LightGray)
        ) {
            feed.invoke {
                coroutineScope.launch {
                    state.goChat()
                }
            }
        }
    }
}