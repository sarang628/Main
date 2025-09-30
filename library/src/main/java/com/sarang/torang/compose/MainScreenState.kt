package com.sarang.torang.compose

import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.main.Alarm
import com.sarang.torang.compose.main.Feed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Stable
class MainScreenState(
    val pagerState: PagerState,
    val navController: NavHostController,
    val feedNavController: NavHostController
) {
    private var job                 : Job?      = null
            var isFeedPage          : Boolean   by mutableStateOf(true); internal set
            var isSwipeEnabled      : Boolean   by mutableStateOf(true)
            var latestDestination   : Any       by mutableStateOf(Feed)
            val currentPage         : Int       get() = pagerState.currentPage
            fun goAlarm()       { navController.navigate(Alarm) }
            fun popToFeed()     { feedNavController.popBackStack("feed", inclusive = false) }
    suspend fun goMain()        { animateScrollToPage(1) }
    suspend fun goAddReview()   { animateScrollToPage(0) }
    suspend fun goChat()        { animateScrollToPage(3) }
    suspend fun animateScrollToPage(page: Int) { pagerState.animateScrollToPage(page) }

    fun swipeDisableForMillis(
        millis: Long = 2000,
        coroutineScope: CoroutineScope) {
        job?.cancel()
        job = coroutineScope.launch {
            isSwipeEnabled = false
            delay(millis)
            isSwipeEnabled = true
        }
    }
}

@Composable
fun rememberMainScreenState(): MainScreenState {
    val tag                     : String                    = "__rememberMainScreenState"
    val coroutineScope          : CoroutineScope            = rememberCoroutineScope()
    val pagerState              : PagerState                = rememberPagerState(initialPage = 1, pageCount = { 3 })
    val navController           : NavHostController         = rememberNavController()
    val feedNavController       : NavHostController         = rememberNavController()
    val onBackPressedDispatcher : OnBackPressedDispatcher?  = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    // 객체는 한 번 만들고, 내부 상태는 mutableStateOf로 반응형 관리
    val state = remember {
        MainScreenState(
            pagerState = pagerState,
            navController = navController,
            feedNavController = feedNavController,
        )
    }

    // 초기 5초 스와이프 비활성화 예시
    LaunchedEffect(Unit) {
        state.isSwipeEnabled = false
        delay(5000)
        state.isSwipeEnabled = true
    }

    // 피드 화면에서만 좌우 스크롤 가능하게
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { entry ->
            state.isFeedPage = entry.destination.route == Feed.toString()
        }
    }

    BackHandler {
        if (state.currentPage != 1) {
            coroutineScope.launch { state.goMain() }
        } else {
            coroutineScope.launch {
                awaitFrame()
                onBackPressedDispatcher?.onBackPressed()
            }
        }
    }

    return state
}