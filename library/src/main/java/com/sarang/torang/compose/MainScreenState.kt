package com.sarang.torang.compose

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.main.Alarm
import com.sarang.torang.compose.main.Feed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainScreenState(
    val pagerState: PagerState,
    val navController: NavHostController,
    val isFeedPage : Boolean,
    val feedNavController: NavHostController,
    var isSwipeEnabled : Boolean,
    var latestDestination : Any
){
    private var job: Job? = null
    val currentPage : Int get() = pagerState.currentPage
    fun goAlarm(){ navController.navigate(Alarm); }
    suspend fun animateScrollToPage(page : Int){ pagerState.animateScrollToPage(page) }
    suspend fun goMain() { animateScrollToPage(1) }
    suspend fun goAddReview() { animateScrollToPage(0) }
    suspend fun goChat() { animateScrollToPage(3) }

    fun popToFeed() {
        feedNavController.popBackStack("feed", inclusive = false) // 피드 화면안에서 다른화면 상태일 때 피드 버튼을 눌렀다면 피드 화면으로 이동
    }

    fun swipeDisableForMillies(
        millies: Long = 2000,
        coroutineScope: CoroutineScope
    ) {
        job?.cancel() // 기존 Job 취소
        job = coroutineScope.launch {
            isSwipeEnabled = false
            delay(millies)
            isSwipeEnabled = true
        }
    }
}

@Composable
fun rememberMainScreenState(): MainScreenState {
    val pagerState = rememberPagerState(
        initialPage = 1,
        pageCount = { 3 }
    )
    val navController = rememberNavController()
    var isFeedPage by remember { mutableStateOf(true) }
    val tag = "__rememberMainScreenState"
    val coroutineScope = rememberCoroutineScope()
    val feedNavController = rememberNavController() // 메인 하단 홈버튼 클릭시 처리를 위해 여기에 설정
    var isSwipeEnabled by remember { mutableStateOf(true) }
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var backPressHandled by remember { mutableStateOf(false) }
    var job: Job? by remember { mutableStateOf(null) }
    var latestDestination: Any by remember { mutableStateOf(Feed) }

    LaunchedEffect(pagerState) { snapshotFlow { pagerState.currentPage }.collect { Log.d(tag, "currentPage : ${pagerState.currentPage}") } }

    LaunchedEffect(key1 = navController.currentDestination) { navController.currentBackStackEntryFlow.collect { isFeedPage = it.destination.route == Feed.toString() } } // 피드 화면에서만 좌우 스크롤 가능하게

    BackHandler(enabled = !backPressHandled) { // 뒤로가기
        if (pagerState.currentPage != 1) { // 첫번째 페이지가 아니라면
            coroutineScope.launch { pagerState.animateScrollToPage(1) } //첫번째 페이지로 이동하기
            return@BackHandler
        }
        backPressHandled = true
        coroutineScope.launch { awaitFrame(); onBackPressedDispatcher?.onBackPressed() }
    }

    LaunchedEffect("") {
        job = launch { isSwipeEnabled = false; delay(5000); isSwipeEnabled = true }
    }

    return remember {
        MainScreenState(
            pagerState = pagerState,
            navController = navController,
            isFeedPage,
            feedNavController,
            isSwipeEnabled,
            latestDestination
        )
    }
}