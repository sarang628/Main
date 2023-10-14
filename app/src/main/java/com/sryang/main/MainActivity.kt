package com.sryang.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.cardinfo.RestaurantCardViewModel
import com.example.screen_feed.FeedsViewModel
import com.example.screen_finding.finding.FindingViewModel
import com.example.screen_main.TorangScreen
import com.example.screen_map.MapViewModel
import com.posco.feedscreentestapp.di.feed.FeedScreen
import com.sarang.profile.viewmodel.ProfileService
import com.sarang.profile.viewmodel.ProfileViewModel
import com.sarang.toringlogin.login.LoginViewModel
import com.sryang.library.AddReviewViewModel
import com.sryang.library.ReviewService
import com.sryang.library.selectrestaurant.SelectRestaurantViewModel
import com.sryang.myapplication.di.profile.ProfileScreen
import com.sryang.screen_filter.ui.FilterViewModel
import com.sryang.screenfindingtest.di.finding.Finding
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
    private val findingViewModel: FindingViewModel by viewModels()
    private val filterViewModel: FilterViewModel by viewModels()

    val profileImageServerUrl = "http://sarang628.iptime.org:89/profile_images/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            TorangScreen(
                lifecycleOwner = this@MainActivity,
                addReviewViewModel = addReviewViewModel,
                loginViewModel = loginViewModel,
                profileViewModel = profileViewModel,
                restaurantInfoViewModel = restaurantInfoViewModel,
                feedScreen = {
                    FeedScreen(
                        onProfile = { navController.navigate("profile/$it") },
                        onRestaurant = {
                            Log.d(
                                "MainActivity",
                                "restaurant/$it"
                            ); navController.navigate("restaurant/$it")
                        },
                        onImage = {},
                        onName = {},
                        clickAddReview = { navController.navigate("addReview") },
                        feedsViewModel = feedsViewModel,
                        profileImageServerUrl = profileImageServerUrl,
                        imageServerUrl = "http://sarang628.iptime.org:89/review_images/"
                    )
                },
                navController = navController,
                selectRestaurantViewModel = selectRestaurantViewModel,
                findingScreen = {
                    Finding(
                        findingViewModel = findingViewModel,
                        restaurantCardViewModel = restaurantCardViewModel,
                        filterViewModel = filterViewModel,
                        mapViewModel = mapViewModel,
                        navController = navController
                    )
                },
                profileScreen = {
                    val id = it.arguments?.getString("id")?.toInt()
                    profileViewModel.loadProfile(id!!)
                    ProfileScreen(
                        profileViewModel = profileViewModel,
                        profileImageUrl = "http://sarang628.iptime.org:89/profile_images/",
                        imageServerUrl = "http://sarang628.iptime.org:89/review_images/",
                        onEditProfile = { navController.navigate("editProfile") },
                        onSetting = { navController.navigate("settings") }
                    )
                },
                onLogout = {
                    loginViewModel.logout {
                        navController.navigate("splash")
                    }
                },
                profileImageServerUrl = profileImageServerUrl
            )
        }
    }
}