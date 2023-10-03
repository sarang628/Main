package com.sryang.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.cardinfo.RestaurantCardViewModel
import com.example.screen_feed.FeedsViewModel
import com.example.screen_main.TorangScreen
import com.example.screen_map.MapViewModel
import com.sarang.profile.move
import com.sryang.di.feed.TestFeedScreen
import com.sarang.profile.viewmodel.ProfileService
import com.sarang.profile.viewmodel.ProfileViewModel
import com.sarang.toringlogin.login.LoginViewModel
import com.sryang.library.ReviewService
import com.sryang.torang_repository.api.ApiReview
import dagger.hilt.android.AndroidEntryPoint
import restaurant_information.RestaurantInfoViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var remoteReviewService: ApiReview

    @Inject
    lateinit var reviewService: ReviewService

    @Inject
    lateinit var profileService: ProfileService

    private val loginViewModel: LoginViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val feedsViewModel: FeedsViewModel by viewModels()
    private val mapViewModel: MapViewModel by viewModels()
    private val restaurantCardViewModel: RestaurantCardViewModel by viewModels()
    private val restaurantInfoViewModel: RestaurantInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            TorangScreen(
                lifecycleOwner = this@MainActivity,
                remoteReviewService = reviewService,
                loginViewModel = loginViewModel,
                profileViewModel = profileViewModel,
                profileUrl = "http://sarang628.iptime.org:89/profile_images/",
                mapViewModel = mapViewModel,
                restaurantCardViewModel = restaurantCardViewModel,
                restaurantInfoViewModel = restaurantInfoViewModel,
                feedScreen = {
                    TestFeedScreen(
                        feedsViewModel = feedsViewModel,
                        onProfile = { navController.navigate("profile/$it") },
                        onRestaurant = {
                            Log.d("MainActivity", "restaurant/$it")
                            navController.navigate("restaurant/$it")
                        },
                        onImage = {},
                        onName = {},
                        onAddReview = { navController.navigate("addReview") }
                    )
                },
                navController = navController
            )
        }
    }
}