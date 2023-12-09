package com.sryang.torang.compose

import android.widget.RatingBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sryang.torang.viewmodels.MainViewModel

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
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit,
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
                        onComment = {
                            mainViewModel.onComment(it)
                        }, onMenu = {
                            mainViewModel.onMenu(it)
                        }, onShare = {
                            mainViewModel.onShare(it)
                        }, onReport = {
                            mainViewModel.onReport(it)
                        }, onReported = {
                            mainViewModel.closeReport()
                        }
                    )
                }
                composable("profile") { myProfileScreen.invoke() }
                composable("finding") { findingScreen.invoke() }
                composable("alarm") { alarm.invoke() }
            }
            MainBottomNavigation(navController = navController)
        }
        if (uiState.showComment != null) {
            commentDialog.invoke(reviewId = uiState.showComment!!, onClose = {
                mainViewModel.closeComment()
            })
        }

        if (uiState.showMenu != null) {
            menuDialog.invoke(
                reviewId = uiState.showMenu!!,
                onClose = {
                    mainViewModel.closeMenu()
                },
                onReport = {
                    mainViewModel.onReport(it)
                },
                onEdit = {
                    mainViewModel.onEdit(it)
                    onEdit.invoke(it)
                },
                onDelete = {
                    mainViewModel.onDelete(it)
                    onDelete.invoke(it)
                }
            )
        }

        if (uiState.showShare) {
            shareDialog.invoke(onClose = {
                mainViewModel.closeShare()
            })
        }

        if (uiState.showReport != null) {
            reportDialog.invoke(uiState.showReport!!,
                onReported = {
                    mainViewModel.closeReport()
                })
        }
    }
}


@Preview
@Composable
fun RatingBar1(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier, factory = {
            RatingBar(it).apply {
                scaleX = 0.4f
                scaleY = 0.4f
                pivotX = 0f
                pivotY = 0f
                stepSize = 0f
            }
        })
}