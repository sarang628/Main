package com.sarang.torang.compose.type

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

typealias FeedScreenType = @Composable (onChat: () -> Unit) -> Unit

val LocalFeedScreenType : ProvidableCompositionLocal<FeedScreenType> = compositionLocalOf<FeedScreenType>(){
    @Composable {

    }
}