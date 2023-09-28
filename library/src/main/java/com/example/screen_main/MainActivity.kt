package com.example.screen_main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cardinfo.RestaurantCardViewModel
import com.example.screen_feed.FeedsViewModel
import com.example.screen_map.MapViewModel
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
            TorangScreen(
                lifecycleOwner = this@MainActivity,
                feedsViewModel = feedsViewModel,
                remoteReviewService = reviewService,
                loginViewModel = loginViewModel,
                profileViewModel = profileViewModel,
                profileUrl = "http://sarang628.iptime.org:89/profile_images/",
                mapViewModel = mapViewModel,
                restaurantCardViewModel = restaurantCardViewModel,
                restaurantInfoViewModel = restaurantInfoViewModel
            )
        }
    }
}