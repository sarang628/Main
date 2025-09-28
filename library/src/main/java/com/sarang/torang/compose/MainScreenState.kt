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
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

class MainScreenState(
    val pagerState: PagerState,
    val navController: NavHostController,
    val isFeedPage : Boolean
){
    val currentPage : Int get() = pagerState.currentPage

    suspend fun animateScrollToPage(page : Int){
        pagerState.animateScrollToPage(page)
    }

    fun goAlarm(){
        navController.navigate(Alarm);
    }

    suspend fun goMain() {
        animateScrollToPage(1)
    }

    suspend fun goAddReview() {
        animateScrollToPage(0)
    }

    suspend fun goChat() {
        animateScrollToPage(3)
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

    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var backPressHandled by remember { mutableStateOf(false) }


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

    return remember {
        MainScreenState(
            pagerState = pagerState,
            navController = navController,
            isFeedPage
        )
    }
}