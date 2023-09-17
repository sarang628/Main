package com.example.screen_main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.screen_feed.FeedsViewModel
import com.sarang.toringlogin.login.LoginViewModel
import com.sryang.torang_repository.services.RemoteReviewService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var feedsViewModel: FeedsViewModel

    @Inject
    lateinit var remoteReviewService: RemoteReviewService

    private val loginViewModel: LoginViewModel by viewModels()

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
                mainLogic = MainLogicImpl(),
                feedsViewModel = feedsViewModel,
                remoteReviewService = remoteReviewService,
                loginViewModel = loginViewModel
            )
        }
    }
}

object MainNavigation {
    const val MAIN = "main"
    const val ADD_REVIEW = "addReview"
}