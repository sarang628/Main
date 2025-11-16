package com.sarang.torang.compose.type

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

typealias AlarmScreenType = @Composable () -> Unit

val LocalAlarmScreenType : ProvidableCompositionLocal<AlarmScreenType> = compositionLocalOf<AlarmScreenType>() {
    @Composable {

    }
}