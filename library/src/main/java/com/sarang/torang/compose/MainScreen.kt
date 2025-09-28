package com.sarang.torang.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sarang.torang.compose.main.Alarm
import com.sarang.torang.compose.main.Feed
import com.sarang.torang.compose.main.FeedGrid
import com.sarang.torang.compose.main.FindingMap
import com.sarang.torang.compose.main.Profile
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
 * @param swipeAblePager 페이지 좌우 스와이프 가능 여부
 * @param alarm 알림 화면
 */
@Composable
fun MainScreen(
    feedScreen          : @Composable (onReview: () -> Unit) -> Unit    = {},
    state               : MainScreenState                               = rememberMainScreenState(),
    feedGrid            : @Composable () -> Unit                        = {},
    findingMapScreen    : @Composable () -> Unit                        = {},
    myProfileScreen     : @Composable () -> Unit                        = {},
    addReview           : @Composable (onClose: () -> Unit) -> Unit     = {},
    chat                : @Composable () -> Unit                        = {},
    alarm               : @Composable () -> Unit                        = {},
    onBottomMenu        : () -> Unit                                    = {},
    swipeAblePager      : Boolean                                       = true,
)
{
    val coroutineScope = rememberCoroutineScope()

    HorizontalPager(state = state.pagerState, userScrollEnabled = state.isFeedPage && swipeAblePager) { // 메인 화면 페이저
        when (MainScreenPager.fromPage(it)) {
            MainScreenPager.ADD_REVIEW -> { addReview.invoke { coroutineScope.launch { state.goMain() } } }

            MainScreenPager.MAIN -> {
                Scaffold(Modifier.fillMaxSize(), bottomBar = {
                    MainBottomNavigationAppBar(
                        navController = state.navController,
                        onBottomMenu = onBottomMenu,
                        onAddReview = { coroutineScope.launch { state.goAddReview() } }
                    )
                }) { padding ->
                    NavHost(navController = state.navController, startDestination = Feed, modifier = Modifier.fillMaxSize()) {
                        composable<Feed>        { feedScreen.invoke { coroutineScope.launch { state.goChat() } } }
                        composable<Profile>     { Box(Modifier.fillMaxSize().padding(padding)) { myProfileScreen.invoke() } }
                        composable<FeedGrid>    { Box(Modifier.fillMaxSize().padding(padding)) { feedGrid.invoke() } }
                        composable<FindingMap>  { Box(Modifier.fillMaxSize().padding(padding)) { findingMapScreen.invoke()} }
                        composable<Alarm>       { Box(Modifier.fillMaxSize().padding(padding)) { alarm.invoke() } }
                    }
                }
            }

            MainScreenPager.CHAT -> { chat.invoke() }
            else -> { Text("화면을 불러오는데 실패하였습니다.") }
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
    )
}