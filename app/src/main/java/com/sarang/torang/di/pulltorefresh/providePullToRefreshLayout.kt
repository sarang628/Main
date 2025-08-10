package com.sarang.torang.di.pulltorefresh

import androidx.compose.runtime.Composable
import com.sryang.library.pullrefresh.PullToRefreshLayoutState

fun providePullToRefreshLayout(state: PullToRefreshLayoutState): @Composable ((isRefreshing: Boolean, onRefresh: (() -> Unit), contents: @Composable (() -> Unit)) -> Unit) {
    return { _, _, content ->
        content.invoke()
    }
}