package com.sarang.torang.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object Alarm // route to Alarm screen

fun NavGraphBuilder.alarmScreen(
    padding: PaddingValues = PaddingValues(0.dp),
    alarm: @Composable () -> Unit = {},
) {
    composable<Alarm> {
        Box(Modifier
            .fillMaxSize()
            .padding(padding)) { alarm.invoke() }
    }
}