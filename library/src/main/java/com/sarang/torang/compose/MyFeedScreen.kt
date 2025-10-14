package com.sarang.torang.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.sarang.torang.viewmodel.FeedDialogsViewModel

@Composable
fun MainMyFeedScreen(
    viewModel: FeedDialogsViewModel,
    myFeedScreen: @Composable (onComment: ((Int) -> Unit), onMenu: ((Int) -> Unit), onShare: ((Int) -> Unit)) -> Unit,
    commentBottomSheet: @Composable (reviewId: Int?) -> Unit,
    menuDialog: @Composable (reviewId: Int, onClose: () -> Unit, onReport: (Int) -> Unit, onDelete: (Int) -> Unit, onEdit: (Int) -> Unit) -> Unit,
    shareDialog: @Composable (onClose: () -> Unit) -> Unit,
    reportDialog: @Composable (Int, onReported: () -> Unit) -> Unit,
    onEdit: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    MainDialogs(
        uiState = uiState,
        shareBottomSheet = shareDialog,
        reportBottomSheet = reportDialog,
        menuBottomSheet = menuDialog,
        onEdit = onEdit,
        commentBottomSheet = commentBottomSheet
    )
}