package com.sarang.torang.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.viewmodel.FeedDialogsViewModel
import com.sarang.torang.viewmodel.MainViewModel

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
        shareDialog = shareDialog,
        reportDialog = reportDialog,
        menuDialog = menuDialog,
        onEdit = onEdit,
        commentBottomSheet = commentBottomSheet
    )
}