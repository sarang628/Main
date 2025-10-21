package com.sarang.torang

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun MainBottomAppBarPreview() {
    Scaffold(
        bottomBar = {
            /*MainBottomNavigationBar1(*//*Preview*//*
                navController = rememberNavController(),
                onAddReview = {}
            )*/
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Blue)
            )
        }
    }
}