package com.sryang.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.screen_main.compose.MainScreen
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sryang.di.feed.FeedScreen
import com.sarang.alarm.compose.AlarmScreen
import com.sryang.myapplication.di.profile.ProfileScreen
import com.sryang.screenfindingtest.di.finding.Finding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val profileImageServerUrl = BuildConfig.PROFILE_IMAGE_SERVER_URL
    private val imageServerUrl = "http://sarang628.iptime.org:89/review_images/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Displaying edge-to-edge
        //WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navController = rememberNavController()
            TorangTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen(
                        feedScreen = {
                            FeedScreen(
                                onProfile = { navController.navigate("profile/$it") },
                                onRestaurant = { navController.navigate("restaurant/$it") },
                                onImage = {},
                                onName = {},
                                clickAddReview = { navController.navigate("addReview") },
                                profileImageServerUrl = profileImageServerUrl,
                                imageServerUrl = imageServerUrl,
                                ratingBar = {}
                            )
                        },
                        findingScreen = {
                            Finding(
                                navController = navController
                            )
                        },
                        myProfileScreen = {
                            ProfileScreen(
                                profileImageUrl = profileImageServerUrl,
                                imageServerUrl = imageServerUrl,
                                onSetting = { navController.navigate("settings") },
                                navBackStackEntry = null
                            )
                        },
                        alarm = { AlarmScreen(profileServerUrl = profileImageServerUrl) }
                    )
                }
            }
        }
    }
}