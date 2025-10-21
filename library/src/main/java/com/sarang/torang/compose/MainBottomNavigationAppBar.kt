package com.sarang.torang.compose

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.main.MainDestination
import com.sarang.torang.navigation.mainNavigationLogic

/**
 * @param onBottomMenu 하단 메뉴 선택 시 이벤트
 */
@OptIn(ExperimentalStdlibApi::class)
@Composable
fun MainBottomNavigationBar(
    modifier                  : Modifier                  = Modifier,
    mainBottomNavigationState : MainBottomNavigationState = rememberMainBottomNavigationState(),
    onBottomMenu              : (MainDestination) -> Unit = { },
    onAddReview               : () -> Unit                = { },
) {
    NavigationBar(
        modifier = modifier.navigationBarsPadding(), //edge-to-edge를 적용 했다면 navigationBarsPadding도 적용 필요
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        MainDestination.entries.forEachIndexed { index, destination ->
            NavigationBarItem(
                selected = mainBottomNavigationState.isSelected(destination),
                onClick = {
                    if (destination == MainDestination.ADD) {
                        onAddReview.invoke()
                        return@NavigationBarItem
                    }
                    onBottomMenu.invoke(destination)
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
