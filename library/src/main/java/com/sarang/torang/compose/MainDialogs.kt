package com.sarang.torang.compose

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun MainDialogs(
    uiState: MainDialogUiState,
    onEdit: (Int) -> Unit,
    reportDialog: @Composable (Int, onReported: () -> Unit) -> Unit,
    menuDialog: @Composable (reviewId: Int, onClose: () -> Unit, onReport: (Int) -> Unit, onDelete: (Int) -> Unit, onEdit: (Int) -> Unit) -> Unit,
    shareDialog: @Composable (onClose: () -> Unit) -> Unit
) {
    if (uiState.showShare) {
        shareDialog.invoke { uiState.mainDialogEvent.onCloseShare() }
    }

    uiState.showReport?.let {
        reportDialog(it, uiState.mainDialogEvent.closeReport)
    }

    uiState.deleteReview?.let {
        AlertDialog(
            onDismissRequest = uiState.mainDialogEvent.onDismissRequest,
            title = { Text(text = "리뷰를 삭제하시겠습니까?", fontSize = 16.sp) },
            confirmButton = {
                Button(onClick = uiState.mainDialogEvent.onDelete) {
                    Text(text = "예")

                }
            }, dismissButton = {
                Button(
                    onClick = uiState.mainDialogEvent.onDismissRequest
                ) {
                    Text(text = "취소")
                }
            })
    }

    uiState.showMenu?.let {
        menuDialog.invoke(
            it, uiState.mainDialogEvent.onCloseMenu,
            uiState.mainDialogEvent.onReport,
            uiState.mainDialogEvent.onDeleteMenu, onEdit
        )
    }
}