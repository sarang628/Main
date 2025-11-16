package com.sarang.torang.compose.type

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

typealias MyProfileScreenType = @Composable () -> Unit

val LocalMyProfileScreenType : ProvidableCompositionLocal<MyProfileScreenType> = compositionLocalOf(){
    @Composable {

    }
}