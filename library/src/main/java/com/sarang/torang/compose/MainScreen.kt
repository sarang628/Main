package com.sarang.torang.compose

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.viewmodels.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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
    commentBottomSheet: @Composable (
        reviewId: Int?,
        onDismissRequest: () -> Unit,
        sheetState: BottomSheetScaffoldState,
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
    val sheetState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false
        )
    )
    val coroutine = rememberCoroutineScope()

    LaunchedEffect(key1 = sheetState.bottomSheetState) {
        snapshotFlow { sheetState.bottomSheetState.currentValue }.collect {
            if (it == SheetValue.Hidden) {
                Log.d("__sryang", "reviewId set null")
                mainViewModel.closeComment()
            }
        }
    }

    Log.d("__sryang", "showComment = ${uiState.showComment.toString()}")
    Box(Modifier.fillMaxSize()) {
        Column {
            val navController = rememberNavController()
            NavHost(
                navController = navController, startDestination = "feed",
                modifier = Modifier.weight(1f)
            ) {
                composable("feed") {
                    feedScreen.invoke(
                        {
                            mainViewModel.onComment(it)
                            coroutine.launch {
                                sheetState.bottomSheetState.expand()
                            }
                        },
                        { mainViewModel.onMenu(it) },
                        { mainViewModel.onShare(it) },
                        { mainViewModel.onReport(it) },
                        { mainViewModel.closeReport() }
                    )

                    commentBottomSheet.invoke(uiState.showComment, {}, sheetState, {
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