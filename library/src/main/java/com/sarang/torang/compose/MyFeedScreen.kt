package com.sarang.torang.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.sarang.torang.compose.type.LocalCommentBottomSheet
import com.sarang.torang.compose.type.LocalFeedScreenType
import com.sarang.torang.compose.type.LocalMenuBottomSheet
import com.sarang.torang.compose.type.LocalReportBottomSheetType
import com.sarang.torang.compose.type.LocalShareBottomSheet
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
    CompositionLocalProvider(LocalShareBottomSheet provides shareDialog,
        LocalReportBottomSheetType provides reportDialog,
        LocalMenuBottomSheet provides menuDialog,
        LocalCommentBottomSheet provides commentBottomSheet,
        LocalFeedScreenType provides { it ->
            myFeedScreen.invoke({}, {}, {})
        }
    ) {
        MainDialogs(
            uiState = uiState,
            onEdit = onEdit,
        )
    }
}