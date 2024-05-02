package com.sarang.torang.compose

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
        onShare: ((Int) -> Unit)
    ) -> Unit,
    findingScreen: @Composable () -> Unit,
    myProfileScreen: @Composable () -> Unit,
    alarm: @Composable () -> Unit,
    commentBottomSheet: @Composable (
        reviewId: Int?,
        onDismissRequest: () -> Unit,
        onBackPressed: () -> Unit,
        content: @Composable (PaddingValues) -> Unit
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
    onBackPressed: (() -> Unit)? = null
) {
    val uiState by mainViewModel.uiState.collectAsState()

    Log.d("__MainScreen", "showComment = ${uiState.showComment.toString()}")
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
                        { mainViewModel.onShare(it) }
                    )

                    commentBottomSheet.invoke(uiState.showComment, {}, {
                        onBackPressed?.invoke()
                    }, {

                    })
                }
                composable("profile") { myProfileScreen.invoke() }
                composable("finding") { findingScreen.invoke() }
                composable("alarm") { alarm.invoke() }
            }
            MainBottomNavigation(navController = navController)
        }

        if (uiState.showMenu != null) {
            menuDialog.invoke(
                uiState.showMenu!!,
                { mainViewModel.closeMenu() },
                { mainViewModel.onReport(it) },
                { mainViewModel.onDelete(it) },
                {
                    mainViewModel.onEdit(it)
                    onEdit.invoke(it)
                }
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