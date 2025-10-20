package com.sarang.torang.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object Add // route to Feed screen

fun NavGraphBuilder.addScreen(
    addReview: @Composable (onClose: () -> Unit) -> Unit = {}
) {
    composable<Add> {
        addReview.invoke {  }
    }
}