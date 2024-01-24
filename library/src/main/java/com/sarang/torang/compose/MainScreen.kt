package com.sarang.torang.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.viewmodels.MainViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    feedScreen: @Composable (
        onComment: ((Int) -> Unit),
        onMenu: ((Int) -> Unit),
        onShare: ((Int) -> Unit),
        onReport: ((Int) -> Unit),
        onReported: (() -> Unit),
    ) -> Unit,
    findingScreen: @Composable () -> Unit,
    myProfileScreen: @Composable () -> Unit,
    alarm: @Composable () -> Unit,
    commentDialog: @Composable (
        reviewId: Int,
        onClose: () -> Unit
    ) -> Unit,
    menuDialog: @Composable (
        reviewId: Int,
        onClose: () -> Unit,
        onReport: (Int) -> Unit,
        onDelete: (Int) -> Unit,
        onEdit: (Int) -> Unit
    ) -> Unit,
    shareDialog: @Composable (
        onClose: () -> Unit
    ) -> Unit,
    reportDialog: @Composable (
        Int, onReported: () -> Unit
    ) -> Unit,
    onEdit: (Int) -> Unit
) {
    val uiState by mainViewModel.uiState.collectAsState()
    Box(Modifier.fillMaxSize()) {
        Column {
            val navController = rememberNavController()
            NavHost(
                navController = navController, startDestination = "feed",
                modifier = Modifier.weight(1f)
            ) {
                composable("feed") {
                    feedScreen.invoke(
                        { mainViewModel.onComment(it) },
                        { mainViewModel.onMenu(it) },
                        { mainViewModel.onShare(it) },
                        { mainViewModel.onReport(it) },
                        { mainViewModel.closeReport() }
                    )
                }
                composable("profile") { myProfileScreen.invoke() }
                composable("finding") { findingScreen.invoke() }
                composable("alarm") { alarm.invoke() }
            }
            MainBottomNavigation(navController = navController)
        }
        if (uiState.showComment != null) {
            commentDialog.invoke(uiState.showComment!!) { mainViewModel.closeComment() }
        }

        if (uiState.showMenu != null) {
            menuDialog.invoke(
                uiState.showMenu!!,
                { mainViewModel.closeMenu() },
                { mainViewModel.onReport(it) },
                {
                    mainViewModel.onEdit(it)
                    onEdit.invoke(it)
                },
                { mainViewModel.onDelete(it) }
            )
        }

        if (uiState.showShare) {
            shareDialog.invoke { mainViewModel.closeShare() }
        }

        if (uiState.showReport != null) {
            reportDialog.invoke(uiState.showReport!!) { mainViewModel.closeReport() }
        }

        if (uiState.deleteReview != null) {
            AlertDialog(
                onDismissRequest = { mainViewModel.dismissDeleteReview() },
                title = { Text(text = "리뷰를 삭제하시겠습니까?", fontSize = 16.sp) },
                confirmButton = {
                    Button(onClick = { mainViewModel.deleteReview() }) {
                        Text(text = "예")

                    }
                }, dismissButton = {
                    Button(onClick = {
                        mainViewModel.dismissDeleteReview()
                    }) {
                        Text(text = "취소")
                    }
                })
        }
    }
}