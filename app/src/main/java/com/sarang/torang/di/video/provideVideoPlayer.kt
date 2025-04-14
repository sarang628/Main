package com.sarang.torang.di.video

import androidx.compose.runtime.Composable

@Composable
fun provideVideoPlayer(): @Composable (url: String, isPlaying: Boolean, onVideoClick: () -> Unit) -> Unit {
    return { _, _, _ ->

    }
}