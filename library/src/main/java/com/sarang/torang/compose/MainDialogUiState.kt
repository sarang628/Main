package com.sarang.torang.compose

data class MainDialogUiState(
    val showShare: Boolean = false,
    val showReport: Int? = null,
    val deleteReview: Int? = null,
    val showMenu: Int? = null,
    val mainDialogEvent: MainDialogEvent
)

data class MainDialogEvent(
    val onCloseShare: () -> Unit,
    val closeReport: () -> Unit,
    val onDismissRequest: () -> Unit,
    val onDelete: () -> Unit,
    val onCloseMenu: () -> Unit,
    val onReport: (Int) -> Unit,
    val onDeleteMenu: (Int) -> Unit,
    val onEdit: (Int) -> Unit,
)