package com.sarang.torang.uistate

data class MainUiState(
    val showComment: Int? = null,
    val showShare: Boolean = false,
    val showMenu: Int? = null,
    val showReport: Int? = null,
    val deleteReview: Int? = null,
    val modifyReview: Int? = null
)
