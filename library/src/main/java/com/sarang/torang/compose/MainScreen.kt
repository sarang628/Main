package com.sarang.torang.compose

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import com.sarang.torang.compose.main.MainDestination
import com.sarang.torang.navigation.Feed
import com.sarang.torang.navigation.alarmScreen
import com.sarang.torang.navigation.feedGridScreen
import com.sarang.torang.navigation.feedScreen
import com.sarang.torang.navigation.findScreen
import com.sarang.torang.navigation.profileScreen
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
 * @param state         메인 화면 상태
 * @param feed          피드 화면
 * @param feedGrid      피드 그리드
 * @param profile       내 프로필 화면
 * @param addReview     리뷰 추가 화면
 * @param chat          채팅 화면
 * @param onBottomMenu  하단바 클릭 리스너
 * @param swipeAble     좌우 스와이프 가능 여부
 * @param alarm         알림 화면
 */
@Preview
@Composable
fun MainScreen(
    state               : MainScreenState                               = rememberMainScreenState(),
    feed                : @Composable (onChat: () -> Unit) -> Unit      = {},
    feedGrid            : @Composable () -> Unit                        = {},
    addReview           : @Composable (onClose: () -> Unit) -> Unit     = {},
    find                : @Composable () -> Unit                        = {},
    profile             : @Composable () -> Unit                        = {},
    chat                : @Composable () -> Unit                        = {},
    alarm               : @Composable () -> Unit                        = {},
    onBottomMenu        : (MainDestination) -> Unit                     = {},
    swipeAble           : Boolean                                       = true,
)
{
    val coroutineScope = rememberCoroutineScope()

    HorizontalPager(
        state = state.pagerState,
        userScrollEnabled = state.isFeedPage && swipeAble
    ) {
        when (MainScreenPager.fromPage(it)) {
            MainScreenPager.ADD_REVIEW -> {
                addReview.invoke { coroutineScope.launch { state.goMain() } }
            }

            MainScreenPager.MAIN -> {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        MainBottomNavigationBar(
                            navController = state.navController,
                            onBottomMenu = onBottomMenu,
                            onAddReview = { coroutineScope.launch { state.goAddReview() } },
                            mainBottomNavigationState = state.mainBottomNavigationState
                        )
                                },
                    contentWindowInsets = WindowInsets(0.dp)
                ) { padding ->
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