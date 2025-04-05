package com.sarang.torang.compose

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.sarang.torang.compose.main.FeedGrid
import com.sarang.torang.compose.main.FindingMap
import com.sarang.torang.compose.main.Profile
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

/**
 * 메인 화면
 *
 * 3개 페이지로 구성된 메인 화면
 *
 * 좌: 리뷰
 * 우: 채팅
 * 중앙: 피드, 피드 그리드, 리뷰 추가, 지도 검색, 프로필
 *
 * @param feedScreen 피드화면
 * @param feedGrid 피드 그리드
 * @param myProfileScreen 내 프로필 화면
 * @param addReview 리뷰 추가 화면
 * @param chat 채팅 화면
 * @param onBottomMenu 하단바 클릭 리스너
 * @param onCloseReview 리뷰 추가 닫기 리스너
 * @param swipeAblePager 페이지 좌우 스와이프 가능 여부
 * @param goAlarm 알림 이동 플래그
 * @param consumeAlarm 알림 이동 후 콜백
 * @param alarm 알림 화면
 */
@Composable
fun MainScreen(
    feedScreen: @Composable (onReview: () -> Unit) -> Unit,
    feedGrid: @Composable () -> Unit,
    findingMapScreen: @Composable () -> Unit,
    myProfileScreen: @Composable () -> Unit,
    addReview: @Composable (onClose: () -> Unit) -> Unit,
    chat: @Composable () -> Unit,
    onBottomMenu: ((Any) -> Unit)? = null,
    swipeAblePager: Boolean = true,
    goAlarm: Boolean = false,
    consumeAlarm: () -> Unit,
    alarm: @Composable () -> Unit,
) {
    val state = rememberPagerState(
        initialPage = 1,
        pageCount = { 3 },
        initialPageOffsetFraction = 0f
    )
    val navController = rememberNavController()
    val coroutine = rememberCoroutineScope()
    var isFeedPage by remember { mutableStateOf(true) }

    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var backPressHandled by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()


    // 뒤로가기
    BackHandler(enabled = !backPressHandled) {
        // 첫번째 페이지가 아니라면
        if (state.currentPage != 1) {
            //첫번째 페이지로 이동하기
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
        }
    }


    // 피드 화면에서만 좌우 스크롤 가능하게
    LaunchedEffect(key1 = navController.currentDestination) {
        navController.currentBackStackEntryFlow.collect {
            isFeedPage = it.destination.route == Feed.toString()
        }
    }

    // 알람 이동 체크
    LaunchedEffect(key1 = goAlarm) {
        if (goAlarm) {
            Log.d("__MainScreen", "goAlarm:$goAlarm")
            navController.navigate(Alarm)
            consumeAlarm.invoke()
        }
    }

    // 메인 화면 페이저
    HorizontalPager(
        state = state,
        userScrollEnabled = isFeedPage && swipeAblePager
    ) {
        when (MainScreenPager.fromPage(it)) {
            MainScreenPager.ADD_REVIEW -> {
                addReview.invoke {
                    coroutine.launch {
                        state.animateScrollToPage(1)
                    }
                }
            }

            MainScreenPager.MAIN -> {
                Scaffold(Modifier.fillMaxSize(), bottomBar = {
                    MainBottomNavigationAppBar(
                        navController = navController,
                        onBottomMenu = onBottomMenu,
                        onAddReview = {
                            coroutine.launch {
                                state.animateScrollToPage(0)
                            }
                        }
                    )
                }) { padding ->
                    NavHost(
                        navController = navController, startDestination = Feed,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable<Feed> {
                            feedScreen.invoke {
                                coroutine.launch {
                                    state.animateScrollToPage(3)
                                }
                            }
                        }
                        composable<Profile> {
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .padding(padding)
                            )
                            {
                                myProfileScreen.invoke()
                            }
                        }
                        composable<FeedGrid> {
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .padding(padding)
                            )
                            {
                                feedGrid.invoke()
                            }
                        }
                        composable<FindingMap> {
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .padding(padding)
                            )
                            {
                                findingMapScreen.invoke()
                            }
                        }
                        composable<Add> { }
                        composable<Alarm> {
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .padding(padding)
                            )
                            {
                                alarm.invoke()
                            }
                        }
                    }
                }
            }

            MainScreenPager.CHAT -> {
                chat.invoke()
            }

            else -> {
                Text("화면을 불러오는데 실패하였습니다.")
            }
        }
    }
}

/**
 * 메인 화면의 각 페이지명
 */
enum class MainScreenPager(val page: Int) {
    ADD_REVIEW(0),
    MAIN(1),
    CHAT(2);

    companion object {
        fun fromPage(page: Int): MainScreenPager? {
            return MainScreenPager.values().firstOrNull {
                it.page == page
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen(/*Preview*/
        feedScreen = { /*TODO*/ },
        feedGrid = { /*TODO*/ },
        myProfileScreen = { /*TODO*/ },
        addReview = { /*TODO*/ },
        chat = { /*TODO*/ },
        findingMapScreen = { /*TODO*/ },
        consumeAlarm = {},
        alarm = {}
    )
}