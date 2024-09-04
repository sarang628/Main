package com.sarang.torang.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.main.Add
import com.sarang.torang.compose.main.Alarm
import com.sarang.torang.compose.main.Feed
import com.sarang.torang.compose.main.Finding
import com.sarang.torang.compose.main.Profile

@Composable
fun MainScreen(
    feedScreen: @Composable () -> Unit,
    findingScreen: @Composable () -> Unit,
    myProfileScreen: @Composable () -> Unit,
    alarm: @Composable () -> Unit,
    onBottomMenu: ((Any) -> Unit)? = null,
) {
    HorizontalPager(
        state = rememberPagerState(
            initialPage = 1,
            pageCount = { 3 },
            initialPageOffsetFraction = 0f
        )
    ) {
        when (it) {
            0 -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFEEEEEE))
                )
            }

            1 -> {
                Column {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController, startDestination = Feed,
                        modifier = Modifier.weight(1f)
                    ) {
                        composable<Feed> { feedScreen.invoke() }
                        composable<Profile> { myProfileScreen.invoke() }
                        composable<Finding> { findingScreen.invoke() }
                        composable<Alarm> { alarm.invoke() }
                        composable<Add> { }
                    }
                    MainBottomNavigationAppBar(
                        navController = navController,
                        onBottomMenu = onBottomMenu
                    )
                }
            }

            2 -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFEEEEEE))
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen(
        feedScreen = { /*TODO*/ },
        findingScreen = { /*TODO*/ },
        myProfileScreen = { /*TODO*/ },
        alarm = { /*TODO*/ })
}