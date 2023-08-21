package com.example.screen_main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MainScreen(
                context = this@MainActivity,
                lifecycleOwner = this@MainActivity,
                navController = navController,
                clickAddReview = {
                    navController.navigate("addReview")
                },
                mainLogic = MainLogicImpl()
            )
        }
    }
}

object MainNavigation {
    const val MAIN = "main"
    const val ADD_REVIEW = "addReview"
}