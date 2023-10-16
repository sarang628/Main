package com.sryang.main

import RestaurantScreen
import SettingsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.cardinfo.RestaurantCardViewModel
import com.example.screen_feed.FeedsViewModel
import com.example.screen_finding.finding.FindingViewModel
import com.example.screen_main.TorangScreen
import com.example.screen_map.MapViewModel
import com.posco.feedscreentestapp.di.feed.FeedScreen
import com.sarang.instagralleryModule.gallery.GalleryScreen
import com.sarang.profile.edit.EditProfileScreen
import com.sarang.profile.move
import com.sarang.profile.viewmodel.ProfileService
import com.sarang.profile.viewmodel.ProfileViewModel
import com.sarang.screen_splash.SplashScreen
import com.sarang.toringlogin.login.LoginScreen
import com.sarang.toringlogin.login.LoginViewModel
import com.sryang.library.AddReviewScreen
import com.sryang.library.AddReviewViewModel
import com.sryang.library.ReviewService
import com.sryang.library.go
import com.sryang.library.selectrestaurant.SelectRestaurantViewModel
import com.sryang.myapplication.di.profile.ProfileScreen
import com.sryang.screen_filter.ui.FilterViewModel
import com.sryang.screenfindingtest.di.finding.Finding
import com.sryang.torang_repository.api.ApiReview
import com.sryang.torang_repository.session.SessionService
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
    val imageServerUrl = "http://sarang628.iptime.org:89/review_images/"
    val restaurantServerUrl = "http://sarang628.iptime.org:89/restaurant_images/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current

            TorangScreen(
                navController = navController,
                feedScreen = {
                    FeedScreen(
                        onProfile = { navController.navigate("profile/$it") },
                        onRestaurant = { navController.navigate("restaurant/$it") },
                        onImage = {},
                        onName = {},
                        clickAddReview = { navController.navigate("addReview") },
                        feedsViewModel = feedsViewModel,
                        profileImageServerUrl = profileImageServerUrl,
                        imageServerUrl = imageServerUrl
                    )
                },
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
                        isMyProfile = false,
                        profileViewModel = profileViewModel,
                        profileImageUrl = profileImageServerUrl,
                        imageServerUrl = imageServerUrl,
                        onEditProfile = { navController.navigate("editProfile") },
                        onSetting = { navController.navigate("settings") }
                    )
                },
                settings = {
                    SettingsScreen(onLogout = {
                        loginViewModel.logout { navController.navigate("splash") }
                    })
                },
                splashScreen = {
                    Column {
                        SplashScreen(onSuccess = {
                            if (SessionService(context).isLogin.value) {
                                navController.move("main")
                            } else {
                                navController.move("login")
                            }
                        })
                    }
                },
                addReviewScreen = {
                    val addReviewNavController = rememberNavController()
                    AddReviewScreen(
                        addReviewViewModel = addReviewViewModel,
                        navController = addReviewNavController,
                        galleryScreen = {
                            GalleryScreen(color = 0xFFFFFBE6, onNext = {
                                addReviewViewModel.selectPictures(it)
                                addReviewNavController.navigate("addReview")
                            }, onClose = {
                                navController.popBackStack()
                            })
                        },
                        selectRestaurantViewModel = selectRestaurantViewModel,
                        onRestaurant = {
                            addReviewViewModel.selectRestaurant(it)
                            addReviewNavController.popBackStack()
                        },
                        onShared = {
                            navController.popBackStack()
                        }
                    )
                },
                loginScreen = {
                    LoginScreen(
                        loginViewModel,
                        onLogin = { navController.go("main") },
                        onLogout = { navController.go("splash") }
                    )
                },
                restaurantScreen = { backStackEntry ->
                    val restaurantId = backStackEntry.arguments?.getString("restaurantId")
                    restaurantId?.let {
                        RestaurantScreen(
                            restaurantId = it.toInt(),
                            restaurantInfoViewModel = restaurantInfoViewModel,
                            reviewImageUrl = imageServerUrl,
                            restaurantImageUrl = restaurantServerUrl
                        )
                    }
                },
                editProfileScreen = {
                    EditProfileScreen(
                        onEditImage = { navController.navigate("EditProfileImage") },
                        profileViewModel = profileViewModel,
                        profileImageServerUrl = profileImageServerUrl,
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                },
                editProfileImageScreen = {
                    GalleryScreen(onNext = {
                        profileViewModel.updateProfileImage(it[0])
                        navController.popBackStack()
                    }, onClose = {
                        navController.popBackStack()
                    })
                },
                myProfileScreen = {
                    profileViewModel.loadProfileByToken()
                    ProfileScreen(
                        isMyProfile = true,
                        profileViewModel = profileViewModel,
                        profileImageUrl = profileImageServerUrl,
                        imageServerUrl = imageServerUrl,
                        onEditProfile = { navController.navigate("editProfile") },
                        onSetting = { navController.navigate("settings") }
                    )
                }
            )
        }
    }
}