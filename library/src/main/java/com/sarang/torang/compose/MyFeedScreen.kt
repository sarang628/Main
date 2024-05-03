package com.sarang.torang.compose

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.viewmodels.MainViewModel

@Composable
fun MainMyFeedScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    myFeedScreen: @Composable (onComment: ((Int) -> Unit), onMenu: ((Int) -> Unit), onShare: ((Int) -> Unit)) -> Unit,
    commentBottomSheet: @Composable (reviewId: Int?) -> Unit,
    menuDialog: @Composable (reviewId: Int, onClose: () -> Unit, onReport: (Int) -> Unit, onDelete: (Int) -> Unit, onEdit: (Int) -> Unit) -> Unit,
    shareDialog: @Composable (onClose: () -> Unit) -> Unit,
    reportDialog: @Composable (Int, onReported: () -> Unit) -> Unit,
    onEdit: (Int) -> Unit
) {
    val uiState by mainViewModel.uiState.collectAsState()

    Log.d("__MainMyFeedScreen", "showComment = ${uiState.showComment.toString()}")
    Box(Modifier.fillMaxSize()) {
        myFeedScreen.invoke(
            { mainViewModel.onComment(it) },
            { mainViewModel.onMenu(it) },
            { mainViewModel.onShare(it) }
        )
        commentBottomSheet.invoke(uiState.showComment)

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