package com.sarang.torang.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.uistate.MainDialogEvent
import com.sarang.torang.uistate.MainDialogUiState
import com.sarang.torang.usecase.DeleteReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedDialogsViewModel @Inject constructor(
    private val deleteReviewUseCase: DeleteReviewUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainDialogUiState(mainDialogEvent = mainDialogEvent()))
    val uiState = _uiState.asStateFlow()

    fun onComment(reviewId: Int) {
        Log.d("__MainViewModel", "onComment reviewId: $reviewId")
        _uiState.update { it.copy(showComment = reviewId) }
    }

    fun onMenu(reviewId: Int) {
        _uiState.update { it.copy(showMenu = reviewId) }
    }

    fun onShare(it: Int) {
        _uiState.update { it.copy(showShare = true) }
    }

    fun closeComment() {
        _uiState.update { it.copy(showComment = null) }
    }

    fun closeMenu() {
        _uiState.update { it.copy(showMenu = null) }
    }

    private fun mainDialogEvent(): MainDialogEvent {
        return MainDialogEvent(
            onCloseShare = {
                _uiState.update {
                    it.copy(showShare = false)
                }
            },
            closeReport = {
                _uiState.update {
                    it.copy(showReport = null)
                }
            },
            onDismissRequest = {
                _uiState.update {
                    it.copy(deleteReview = null)
                }
            },
            onDelete = {
                viewModelScope.launch {
                    uiState.value.deleteReview?.let {
                        deleteReviewUseCase.invoke(it)
                    }
                    _uiState.update { it.copy(deleteReview = null) }
                }
            },
            onCloseMenu = { _uiState.update { it.copy(showMenu = null) } },
            onReport = { reviewId ->
                _uiState.update {
                    it.copy(
                        showReport = reviewId,
                        showMenu = null
                    )
                }
            },
            onDeleteMenu = { reviewId ->
                _uiState.update {
                    it.copy(
                        deleteReview = reviewId,
                        showMenu = null
                    )
                }
            }
        )
    }
}