package com.example.screen_main.compose

import android.widget.RatingBar
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(
    feedScreen: @Composable () -> Unit,
    findingScreen: @Composable () -> Unit,
    myProfileScreen: @Composable () -> Unit,
    alarm: @Composable () -> Unit,
) {
    Column {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = "feed",
            modifier = Modifier.weight(1f)
        ) {
            composable("feed") { feedScreen.invoke() }
            composable("myProfile") { myProfileScreen.invoke() }
            composable("finding") { findingScreen.invoke() }
            composable("alarm") { alarm.invoke() }
        }
        MainBottomNavigation1(navController = navController)
    }
}


@Preview
@Composable
fun RatingBar1(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier, factory = {
            RatingBar(it).apply {
                scaleX = 0.4f
                scaleY = 0.4f
                pivotX = 0f
                pivotY = 0f
                stepSize = 0f
            }
        })
}