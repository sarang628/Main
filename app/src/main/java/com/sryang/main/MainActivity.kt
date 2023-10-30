package com.sryang.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.screen_main.compose.MainScreen
import com.posco.feedscreentestapp.di.feed.FeedScreen
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
            MainScreen(
                feedScreen = {
                    FeedScreen(
                        onProfile = { navController.navigate("profile/$it") },
                        onRestaurant = { navController.navigate("restaurant/$it") },
                        onImage = {},
                        onName = {},
                        clickAddReview = { navController.navigate("addReview") },
                        profileImageServerUrl = profileImageServerUrl,
                        imageServerUrl = imageServerUrl
                    )
                },
                findingScreen = {
                    Finding(
                        navController = navController
                    )
                },
                myProfileScreen = {
                    ProfileScreen(
                        isMyProfile = true,
                        profileImageUrl = profileImageServerUrl,
                        imageServerUrl = imageServerUrl,
                        onEditProfile = { navController.navigate("editProfile") },
                        onSetting = { navController.navigate("settings") }
                    )
                },
                alarm = { AlarmScreen(profileServerUrl = profileImageServerUrl) }
            )
        }
    }
}