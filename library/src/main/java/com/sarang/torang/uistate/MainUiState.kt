package com.sarang.torang.uistate

import com.sarang.torang.compose.MainDialogUiState

data class MainUiState(
    val showComment: Int? = null,
    val modifyReview: Int? = null,
    val dialogUiState: MainDialogUiState,
)
