package com.sarang.torang.compose.type

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

typealias AddReviewScreenType = @Composable (onClose: () -> Unit) -> Unit

val LocalAddReviewScreenType : ProvidableCompositionLocal<AddReviewScreenType> = compositionLocalOf<AddReviewScreenType>(){
    @Composable {

    }
}