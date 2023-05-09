package com.example.screen_main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*setContent {
            val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
            TorangApp(
                //appContainer,
                widthSizeClass
            )
        }*/
    }
}


@Composable
fun TorangApp(
    //appContainer: AppContainer,
    widthSizeClass: WindowWidthSizeClass
) {
    TorangTheme {

    }
}

@Composable
fun TorangTheme(
    content: @Composable () -> Unit
) {

}