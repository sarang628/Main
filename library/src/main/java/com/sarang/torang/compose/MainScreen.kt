package com.sarang.torang.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.viewmodels.MainViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    feedScreen: @Composable (onComment: ((Int) -> Unit), onMenu: ((Int) -> Unit), onShare: ((Int) -> Unit), navBackStackEntry: NavBackStackEntry) -> Unit,
    findingScreen: @Composable () -> Unit,
    myProfileScreen: @Composable () -> Unit,
    alarm: @Composable () -> Unit,
    commentBottomSheet: @Composable (reviewId: Int?) -> Unit,
    menuDialog: @Composable (reviewId: Int, onClose: () -> Unit, onReport: (Int) -> Unit, onDelete: (Int) -> Unit, onEdit: (Int) -> Unit) -> Unit,
    shareDialog: @Composable (onClose: () -> Unit) -> Unit,
    reportDialog: @Composable (Int, onReported: () -> Unit) -> Unit,
    onEdit: (Int) -> Unit,
    onBottomMenu: ((String) -> Unit)? = null
) {
    val uiState by mainViewModel.uiState.collectAsState()


    Box(Modifier.fillMaxSize()) {
        Column {
            val navController = rememberNavController()
            NavHost(
                navController = navController, startDestination = "feed",
                modifier = Modifier.weight(1f)
            ) {
                composable("feed") { navBackStackEntry ->
                    feedScreen.invoke(
                        { mainViewModel.onComment(it) },
                        { mainViewModel.onMenu(it) },
                        { mainViewModel.onShare(it) },
                        navBackStackEntry
                    )
                    commentBottomSheet.invoke(uiState.showComment)
                }
                composable("profile") { myProfileScreen.invoke() }
                composable("finding") { findingScreen.invoke() }
                composable("alarm") { alarm.invoke() }
            }
            MainBottomNavigation(navController = navController, onBottomMenu = onBottomMenu)
        }

        MainDialogs(
            uiState = uiState.dialogUiState,
            shareDialog = shareDialog,
            reportDialog = reportDialog,
            menuDialog = menuDialog,
            onEdit = {
                uiState.dialogUiState.mainDialogEvent.onEdit(it)
                onEdit.invoke(it)
            }
        )
    }
}