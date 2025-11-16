package com.sarang.torang.compose

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.main.MainDestination
import com.sarang.torang.navigation.Feed

/**
 * @param onBottomMenu 하단 메뉴 선택 시 이벤트
 */
@OptIn(ExperimentalStdlibApi::class)
@Preview
@Composable
fun MainBottomNavigationBar(
    modifier                  : Modifier                  = Modifier,
    bottomNavBarHeight        : Dp                        = 80.dp,
    mainBottomNavigationState : MainBottomNavigationState = rememberMainBottomNavigationState(),
    onBottomMenu              : (MainDestination) -> Unit = {},
    onAddReview               : () -> Unit                = {},
    onAlreadyFeed             : () -> Unit                = {}
) {
    val tag = "__MainBottomNavigationBar"
    val currentDestination = mainBottomNavigationState.currentDestination
    NavigationBar(
        modifier = modifier.height(bottomNavBarHeight).navigationBarsPadding(), //edge-to-edge를 적용 했다면 navigationBarsPadding도 적용 필요
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        MainDestination.entries.forEachIndexed { index, destination ->
            NavigationBarItem(
                selected = mainBottomNavigationState.isSelectedDestination(destination),
                onClick = {
                    if(destination == MainDestination.FEED &&
                        mainBottomNavigationState.lastRoute == MainDestination.FEED){
                        onAlreadyFeed()
                        return@NavigationBarItem
                    }

                    if(currentDestination.isRouteInHierarchy(destination.baseRoute))
                        return@NavigationBarItem

                    if (destination == MainDestination.ADD) {
                        onAddReview.invoke()
                        return@NavigationBarItem
                    }
                    onBottomMenu.invoke(destination)
                    mainBottomNavigationState.lastRoute = destination
                },
                icon = { Icon(imageVector = destination.icon, contentDescription = null) },
            )
        }
    }
}

@Composable
fun rememberMainBottomNavigationState(navHostController: NavHostController = rememberNavController()) : MainBottomNavigationState {
    return remember { MainBottomNavigationState(navHostController) }
}
