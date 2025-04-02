package com.sarang.torang.compose

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.torang.uistate.MainDialogUiState

/**
 * 메인 다이얼로그
 * 메인 화면에서 사용하는 다이얼로그
 * 리뷰 신고, 리뷰 메뉴, 리뷰 공유, 리뷰 댓글 보기/작성 다이얼로그 등
 * @param uiState 다이얼로그 상태
 * @param onEdit 리뷰 수정
 * @param reportDialog 리뷰 신고 다이얼로그
 * @param menuDialog 리뷰 메뉴 다이얼로그
 * @param shareDialog 리뷰 공유 다이얼로그
 * @param commentBottomSheet 리뷰 댓글 보기/작성 다이얼로그
 * @param contents 화면
 */
@Composable
fun MainDialogs(
    uiState: MainDialogUiState,
    onEdit: (Int) -> Unit,
    reportDialog: @Composable (Int, onReported: () -> Unit) -> Unit,
    menuDialog: @Composable (reviewId: Int, onClose: () -> Unit, onReport: (Int) -> Unit, onDelete: (Int) -> Unit, onEdit: (Int) -> Unit) -> Unit,
    shareDialog: @Composable (onClose: () -> Unit) -> Unit,
    commentBottomSheet: @Composable (reviewId: Int?) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {


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
            Log.d("__MainDialogs", "showMenu reviewId: $it")
            menuDialog.invoke(
                it, uiState.mainDialogEvent.onCloseMenu,
                uiState.mainDialogEvent.onReport,
                uiState.mainDialogEvent.onDeleteMenu, onEdit
            )
        }


        commentBottomSheet.invoke(uiState.showComment)
    }
}