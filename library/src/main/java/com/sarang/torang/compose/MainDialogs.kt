package com.sarang.torang.compose

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.sarang.torang.compose.type.LocalCommentBottomSheet
import com.sarang.torang.compose.type.LocalMenuBottomSheet
import com.sarang.torang.compose.type.LocalReportBottomSheetType
import com.sarang.torang.compose.type.LocalRestaurantBottomSheet
import com.sarang.torang.compose.type.LocalShareBottomSheet
import com.sarang.torang.uistate.MainDialogUiState

/**
 * 메인 다이얼로그
 * 메인 화면에서 사용하는 다이얼로그
 * 리뷰 신고, 리뷰 메뉴, 리뷰 공유, 리뷰 댓글 보기/작성 다이얼로그 등
 * @param uiState 다이얼로그 상태
 * @param onEdit 리뷰 수정
 * @param contents 화면
 */
@Preview
@Composable
fun MainDialogs(
    uiState                 : MainDialogUiState                                  = MainDialogUiState(),
    onEdit                  : (Int) -> Unit                                      = {},
) {
    Box(modifier = Modifier.fillMaxSize()) {


        if (uiState.showShare) {
            LocalShareBottomSheet.current.invoke { uiState.mainDialogEvent.onCloseShare() }
        }

        uiState.showReport?.let {
            LocalReportBottomSheetType.current(it, uiState.mainDialogEvent.closeReport)
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
            LocalMenuBottomSheet.current.invoke(
                it, uiState.mainDialogEvent.onCloseMenu,
                uiState.mainDialogEvent.onReport,
                uiState.mainDialogEvent.onDeleteMenu, onEdit
            )
        }

        LocalRestaurantBottomSheet.current.invoke(
            {
                LocalCommentBottomSheet.current.invoke(uiState.showComment)
            }
        )
    }
}