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
import com.posco.feedscreentestapp.di.feed.FeedScreen
import com.sarang.profile.viewmodel.ProfileService
import com.sarang.profile.viewmodel.ProfileViewModel
import com.sarang.toringlogin.login.LoginViewModel
import com.sryang.library.AddReviewViewModel
import com.sryang.library.ReviewService
import com.sryang.library.selectrestaurant.SelectRestaurantViewModel
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
    private val addReviewViewModel: AddReviewViewModel by viewModels()
    private val selectRestaurantViewModel: SelectRestaurantViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            TorangScreen(
                lifecycleOwner = this@MainActivity,
                addReviewViewModel = addReviewViewModel,
                loginViewModel = loginViewModel,
                profileViewModel = profileViewModel,
                profileUrl = "http://sarang628.iptime.org:89/profile_images/",
                mapViewModel = mapViewModel,
                restaurantCardViewModel = restaurantCardViewModel,
                restaurantInfoViewModel = restaurantInfoViewModel,
                feedScreen = {
                    FeedScreen(
                        onProfile = { navController.navigate("profile/$it") },
                        onRestaurant = { Log.d("MainActivity", "restaurant/$it"); navController.navigate("restaurant/$it") },
                        onImage = {},
                        onName = {},
                        clickAddReview = { navController.navigate("addReview") },
                        feedsViewModel = feedsViewModel,
                        profileImageServerUrl = "http://sarang628.iptime.org:89/profile_images/",
                        imageServerUrl = "http://sarang628.iptime.org:89/review_images/"
                    )
                },
                navController = navController,
                selectRestaurantViewModel = selectRestaurantViewModel
            )
        }
    }
}