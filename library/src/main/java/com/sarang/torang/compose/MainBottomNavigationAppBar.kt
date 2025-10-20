package com.sarang.torang.compose

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.main.MainDestination
import com.sarang.torang.navigation.Feed
import com.sarang.torang.navigation.addScreen
import com.sarang.torang.navigation.feedGridScreen
import com.sarang.torang.navigation.feedScreen
import com.sarang.torang.navigation.findScreen
import com.sarang.torang.navigation.mainNavigationLogic
import com.sarang.torang.navigation.profileScreen
import kotlin.enums.EnumEntries
import kotlin.enums.enumEntries

/**
 * @param onBottomMenu 하단 메뉴 선택 시 이벤트
 */
@OptIn(ExperimentalStdlibApi::class)
@Composable
fun MainBottomNavigationBar(
    modifier                    : Modifier                  = Modifier,
    mainBottomNavigationState   : MainBottomNavigationState = rememberMainBottomNavigationState(),
    navController               : NavController,
    onBottomMenu                : (Any) -> Unit             = { },
    onAddReview                 : () -> Unit                = { },
) {

    val currentDestination: NavDestination? = navController.currentDestination

    MainBottomNavigationBar(
        modifier                    = modifier.height(80.dp),
        items                       = MainDestination.entries,
        mainBottomNavigationState   = mainBottomNavigationState,
        route                       = /*currentDestination*/MainDestination.FEED, //TODO::현재 경로 처리
        onBottomMenu                = {
            mainNavigationLogic(
                destination = it,
                onAddReview = onAddReview,
                navController = navController,
                onBottomMenu = onBottomMenu
            )
        })

}

@OptIn(ExperimentalStdlibApi::class)
@Composable
private fun MainBottomNavigationBar(
    modifier                    : Modifier                      = Modifier,
    mainBottomNavigationState   : MainBottomNavigationState     = rememberMainBottomNavigationState(),
    items                       : EnumEntries<MainDestination>  = enumEntries<MainDestination>()   ,
    route                       : MainDestination?              = null,
    onBottomMenu                : (MainDestination) -> Unit     = { },
) {
    NavigationBar(
        modifier = modifier.navigationBarsPadding(), //edge-to-edge를 적용 했다면 navigationBarsPadding도 적용 필요
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                selected = route?.route == screen.route,
                onClick = {
                    onBottomMenu.invoke(screen)
                    mainBottomNavigationState.lastDestination = screen
                },
                icon = { Icon(imageVector = screen.icon, contentDescription = null) },
            )
        }
    }
}

class MainBottomNavigationState {
    var lastDestination : Any = mutableStateOf(Feed)
}


@Composable
fun rememberMainBottomNavigationState() : MainBottomNavigationState {
    return remember { MainBottomNavigationState() }
}

@OptIn(ExperimentalStdlibApi::class)
@Preview(showBackground = true)
@Composable
fun MainBottomAppBarPreview() {
    val navController = rememberNavController()
    var currentDestination: MainDestination by remember { mutableStateOf(MainDestination.FEED) }
    val state = rememberMainScreenState()

    Box {
        NavHost(navController = navController, startDestination = currentDestination) {
            feedScreen(state = state)/*preview*/
            feedGridScreen()/*preview*/
            findScreen()/*preview*/
            profileScreen()/*preview*/
            addScreen()/*preview*/
        }
    }

    MainBottomNavigationBar(/*preview*/
        items = MainDestination.entries,
        route = currentDestination,
        onBottomMenu = {
            Log.d("__Preview", it.toString())
            currentDestination = it
        }
    )
}