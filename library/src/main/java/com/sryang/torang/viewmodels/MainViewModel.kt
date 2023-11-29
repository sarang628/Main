package com.sryang.torang.viewmodels

import androidx.lifecycle.ViewModel
import com.sryang.torang.uistate.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
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

    fun closeReport() {
        _uiState.update { it.copy(showReport = null) }
    }
}