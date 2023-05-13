package com.example.screen_main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navigation = MutableStateFlow<String>(MainNavigation.MAIN)

        setContent {
            val nav by navigation.collectAsState()
            MainScreen(
                context = this@MainActivity,
                lifecycleOwner = this@MainActivity,
                nav,
                clickAddReview = {
                    lifecycleScope.launch {
                        navigation.emit(MainNavigation.ADD_REVIEW)
                    }
                }
            )
        }
    }
}

object MainNavigation {
    const val MAIN = "main"
    const val ADD_REVIEW = "addReview"
}