package com.sarang.torang.compose.type

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

typealias FindScreenType = @Composable () -> Unit

val LocalFindScreenType : ProvidableCompositionLocal<FindScreenType> = compositionLocalOf<FindScreenType>() {
    @Composable {

    }
}

