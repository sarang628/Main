package com.sarang.torang.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sarang.torang.compose.type.LocalFindScreenType
import kotlinx.serialization.Serializable

@Serializable
data object Find // route to Feed screen

fun NavController.navigateToFind(navOptions: NavOptions) = navigate(route = Find, navOptions)

fun NavGraphBuilder.findScreen(
    padding: PaddingValues = PaddingValues(0.dp),
) {
    composable<Find> {
        Box(Modifier
            .fillMaxSize()
            .padding(padding)) { LocalFindScreenType.current.invoke() }
    }
}