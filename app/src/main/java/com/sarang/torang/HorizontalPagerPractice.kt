package com.sarang.torang

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun HorizontalPagerPractice() {
    val pagerState = rememberPagerState { 3 }

    Box(Modifier.fillMaxSize()) {

        Text("CurrentPage : ${pagerState.currentPage}")

        HorizontalPager(pagerState) { page ->
            when (page) {
                0 -> {
                    Box(Modifier.fillMaxSize()){
                        Text(modifier = Modifier.align(Alignment.Center), text = "0", fontSize = 50.sp)
                    }
                }
                1 -> {
                    Box(Modifier.fillMaxSize()){
                        Text(modifier = Modifier.align(Alignment.Center), text = "1", fontSize = 50.sp)
                    }
                }
                2 -> {
                    Box(Modifier.fillMaxSize()){
                        Text(modifier = Modifier.align(Alignment.Center), text = "2", fontSize = 50.sp)
                    }
                }
            }
        }
    }
}
