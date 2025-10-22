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
import com.sarang.torang.compose.main.MainDestination
import com.sarang.torang.navigation.Alarm
import com.sarang.torang.navigation.Feed
import com.sarang.torang.navigation.navigateToMainDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Stable
class MainScreenState(
    val pagerState: PagerState,
    val navController: NavHostController,
    val feedNavController: NavHostController,
    val mainBottomNavigationState: MainBottomNavigationState
) {
    private var job                 : Job?      = null
            var isFeedPage          : Boolean   by mutableStateOf(true); internal set
            var isSwipeEnabled      : Boolean   by mutableStateOf(true)
            var latestDestination   : Any       by mutableStateOf(Feed); internal set
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

    @Composable
    fun isFeedOnTop(it: MainDestination) : Boolean {
        return mainBottomNavigationState.isSelectedDestination(it) && mainBottomNavigationState.isSelectedDestination(MainDestination.FEED)
    }

    fun navigate(destination: MainDestination) {
        navController.navigateToMainDestination(destination)
    }
}

val MainScreenState.currentScreen : MainScreenPager get() = when(this.currentPage){
    0 ->{ MainScreenPager.ADD_REVIEW }
    1 ->{ MainScreenPager.MAIN }
    2 ->{ MainScreenPager.CHAT }
    else -> { throw Exception("") }
}

val MainScreenState.isMain : Boolean get() = currentScreen == MainScreenPager.MAIN

@Composable
fun rememberMainScreenState(): MainScreenState {
    val tag                         : String                    = "__rememberMainScreenState"
    val coroutineScope              : CoroutineScope            = rememberCoroutineScope()
    val pagerState                  : PagerState                = rememberPagerState(initialPage = 1, pageCount = { 3 })
    val navController               : NavHostController         = rememberNavController()
    val feedNavController           : NavHostController         = rememberNavController()
    val mainBottomNavigationState   : MainBottomNavigationState = rememberMainBottomNavigationState(navController)
    val onBackPressedDispatcher     : OnBackPressedDispatcher?  = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var interceptBackPressedHandled : Boolean                   by remember { mutableStateOf(true) }

    // 객체는 한 번 만들고, 내부 상태는 mutableStateOf로 반응형 관리
    val state = remember {
        MainScreenState(
            pagerState = pagerState,
            navController = navController,
            feedNavController = feedNavController,
            mainBottomNavigationState = mainBottomNavigationState
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

    BackHandler(interceptBackPressedHandled) {
        if (!state.isMain) {
            coroutineScope.launch { state.goMain() }
        } else {
            interceptBackPressedHandled = false
            coroutineScope.launch {
                awaitFrame()
                onBackPressedDispatcher?.onBackPressed()
                delay(100)
                interceptBackPressedHandled = true
            }
        }
    }

    return state
}