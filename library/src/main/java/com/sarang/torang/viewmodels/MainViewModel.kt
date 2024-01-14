package com.sarang.torang.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val deleteReviewUseCase: DeleteReviewUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    fun onComment(reviewId: Int) {
        _uiState.update { it.copy(showComment = reviewId) }
    }

    fun closeComment() {
        _uiState.update { it.copy(showComment = null) }
    }

    fun onMenu(reviewId: Int) {
        _uiState.update { it.copy(showMenu = reviewId) }
    }

    fun closeMenu() {
        _uiState.update { it.copy(showMenu = null) }
    }

    fun onShare(it: Int) {
        _uiState.update { it.copy(showShare = true) }
    }

    fun closeShare() {
        _uiState.update { it.copy(showShare = false) }
    }

    fun onReport(reviewId: Int) {
        _uiState.update { it.copy(showReport = reviewId, showMenu = null) }
    }

    fun onEdit(reviewId: Int) {
        _uiState.update { it.copy(modifyReview = reviewId, showMenu = null) }
    }

    fun onDelete(reviewId: Int) {
        _uiState.update { it.copy(deleteReview = reviewId, showMenu = null) }
    }

    fun closeReport() {
        _uiState.update { it.copy(showReport = null) }
    }

    fun dismissDeleteReview() {
        _uiState.update { it.copy(deleteReview = null) }
    }

    fun deleteReview() {
        viewModelScope.launch {
            uiState.value.deleteReview?.let {
                deleteReviewUseCase.invoke(it)
            }
            dismissDeleteReview()
        }
    }
}