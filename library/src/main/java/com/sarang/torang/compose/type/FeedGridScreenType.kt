package com.sarang.torang.compose.type

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

typealias FeedGridScreenType = @Composable () -> Unit

val LocalFeedGridScreenType : ProvidableCompositionLocal<FeedGridScreenType> = compositionLocalOf<FeedGridScreenType>(){
    @Composable {

    }
}