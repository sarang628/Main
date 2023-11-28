package com.sryang.torang.uistate

data class MainUiState(
    val showComment: Boolean = false,
    val showShare: Boolean = false,
    val showMenu: Int? = null,
    val showReport: Int? = null
)
