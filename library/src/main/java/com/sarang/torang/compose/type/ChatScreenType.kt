package com.sarang.torang.compose.type

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

typealias ChatScreenType = @Composable () -> Unit

val LocalChatScreenType : ProvidableCompositionLocal<ChatScreenType> = compositionLocalOf<ChatScreenType>(){
    @Composable {

    }
}