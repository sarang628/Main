package com.sarang.torang.compose

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

/**
 * 메인 화면
 *
 * 3개 페이지로 구성된 메인 화면
 *
 * 좌: 리뷰, 우: 채팅, 중앙: 피드, 음식점 찾기, 리뷰 추가, 알람, 프로필
 *
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
    swipeAblePager: Boolean = true,
) {
    val state = rememberPagerState(
        initialPage = 1,
        pageCount = { 3 },
        initialPageOffsetFraction = 0f
    )
    val navController = rememberNavController()
    val coroutine = rememberCoroutineScope()
    var userScrollEnabled by remember { mutableStateOf(true) }

    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var backPressHandled by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()



    BackHandler(enabled = !backPressHandled) {
        if (state.currentPage != 1) {
            coroutineScope.launch {
                state.animateScrollToPage(1)
            }
            return@BackHandler
        }

        backPressHandled = true
        coroutineScope.launch {
            Log.d("__MainScreen", "onBackPressed backPressHandled:$backPressHandled")
            awaitFrame()
            onBackPressedDispatcher?.onBackPressed()
            //backPressHandled = false
        }
    }


    // 피드 화면에서만 좌우 스크롤 가능하게
    LaunchedEffect(key1 = navController.currentDestination) {
        navController.currentBackStackEntryFlow.collect {
            userScrollEnabled = it.destination.route == Feed.toString()
        }
    }

    HorizontalPager(
        state = state,
        userScrollEnabled = userScrollEnabled && swipeAblePager
    ) {
        when (it) {
            0 -> {
                addReview.invoke {
                    coroutine.launch {
                        state.animateScrollToPage(1)
                    }
                }
            }

            1 -> {
                Column {
                    val navController = navController
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
    MainScreen(/*Preview*/
        feedScreen = { /*TODO*/ },
        findingScreen = { /*TODO*/ },
        myProfileScreen = { /*TODO*/ },
        alarm = { /*TODO*/ },
        addReview = { /*TODO*/ },
        chat = { /*TODO*/ }
    )
}