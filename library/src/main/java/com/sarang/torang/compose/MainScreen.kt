package com.sarang.torang.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.main.Add
import com.sarang.torang.compose.main.Alarm
import com.sarang.torang.compose.main.Feed
import com.sarang.torang.compose.main.Finding
import com.sarang.torang.compose.main.Profile
import kotlinx.coroutines.launch

/**
 * 메인 화면
 * @param feedScreen 피드화면
 * @param findingScreen 음식점 찾기 화면
 * @param myProfileScreen 내 프로필 화면
 * @param alarm 알람 화면
 * @param addReview 리뷰 추가 화면
 * @param chat 채팅 화면
 * @param onBottomMenu 하단바 클릭 리스너
 * @param onCloseReview 리뷰 추가 닫기 리스너
 */
@Composable
fun MainScreen(
    feedScreen: @Composable (
        onReview: () -> Unit,
    ) -> Unit,
    findingScreen: @Composable () -> Unit,
    myProfileScreen: @Composable () -> Unit,
    alarm: @Composable () -> Unit,
    addReview: @Composable (onClose: () -> Unit) -> Unit,
    chat: @Composable () -> Unit,
    onBottomMenu: ((Any) -> Unit)? = null,
    onCloseReview: (() -> Unit),
) {
    val state = rememberPagerState(
        initialPage = 1,
        pageCount = { 3 },
        initialPageOffsetFraction = 0f
    )
    val coroutine = rememberCoroutineScope()
    HorizontalPager(
        state = state
    ) {
        when (it) {
            0 -> {
                addReview.invoke(onCloseReview)
            }

            1 -> {
                Column {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController, startDestination = Feed,
                        modifier = Modifier.weight(1f)
                    ) {
                        composable<Feed> {
                            feedScreen.invoke {
                                coroutine.launch {
                                    state.animateScrollToPage(3)
                                }
                            }
                        }
                        composable<Profile> { myProfileScreen.invoke() }
                        composable<Finding> { findingScreen.invoke() }
                        composable<Alarm> { alarm.invoke() }
                        composable<Add> { }
                    }
                    MainBottomNavigationAppBar(
                        navController = navController,
                        onBottomMenu = onBottomMenu,
                        onAddReview = {
                            coroutine.launch {
                                state.animateScrollToPage(0)
                            }
                        }
                    )
                }
            }

            2 -> {
                chat.invoke()
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
        alarm = { /*TODO*/ },
        addReview = { /*TODO*/ },
        chat = { /*TODO*/ },
        onCloseReview = {}
    )
}