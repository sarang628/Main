package com.sarang.torang.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.compose.MainDialogEvent
import com.sarang.torang.compose.MainDialogUiState
import com.sarang.torang.uistate.MainUiState
import com.sarang.torang.usecase.DeleteReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val deleteReviewUseCase: DeleteReviewUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        MainUiState(
            dialogUiState = MainDialogUiState(mainDialogEvent = mainDialogEvent())
        )
    )
    val uiState = _uiState.asStateFlow()


    private fun mainDialogEvent(): MainDialogEvent {
        return MainDialogEvent(
            onCloseShare = {
                _uiState.update {
                    it.copy(
                        dialogUiState = it.dialogUiState.copy(
                            showShare = false
                        )
                    )
                }
            },
            closeReport = {
                _uiState.update {
                    it.copy(
                        dialogUiState = it.dialogUiState.copy(
                            showReport = null
                        )
                    )
                }
            },
            onDismissRequest = {
                _uiState.update {
                    it.copy(
                        dialogUiState = it.dialogUiState.copy(
                            deleteReview = null
                        )
                    )
                }
            },
            onDelete = {
                viewModelScope.launch {
                    uiState.value.dialogUiState.deleteReview?.let {
                        deleteReviewUseCase.invoke(it)
                    }
                    _uiState.update { it.copy(dialogUiState = it.dialogUiState.copy(deleteReview = null)) }
                }
            },
            onCloseMenu = { _uiState.update { it.copy(dialogUiState = it.dialogUiState.copy(showMenu = null)) } },
            onReport = { reviewId ->
                _uiState.update {
                    it.copy(
                        dialogUiState = it.dialogUiState.copy(
                            showReport = reviewId,
                            showMenu = null
                        )
                    )
                }
            },
            onDeleteMenu = { reviewId ->
                _uiState.update {
                    it.copy(
                        dialogUiState = it.dialogUiState.copy(
                            deleteReview = reviewId,
                            showMenu = null
                        )
                    )
                }
            },
            onEdit = { reviewId ->
                _uiState.update {
                    it.copy(
                        modifyReview = reviewId,
                        dialogUiState = it.dialogUiState.copy(showMenu = null)
                    )
                }
            }
        )
    }

    fun onComment(reviewId: Int) {
        _uiState.update { it.copy(showComment = reviewId) }
    }

    fun onMenu(reviewId: Int) {
        _uiState.update { it.copy(dialogUiState = it.dialogUiState.copy(showMenu = reviewId)) }
    }

    fun onShare(it: Int) {
        _uiState.update { it.copy(dialogUiState = it.dialogUiState.copy(showShare = true)) }
    }
}