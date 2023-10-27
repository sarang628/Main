package com.sryang.main


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.di.restaurant_detail.toFeedUiState
import com.example.screen_main.compose.MainScreen
import com.example.screen_main.compose.TorangScreen
import com.example.torangscreensettings.compose.SettingsScreen
import com.posco.feedscreentestapp.di.feed.FeedScreen
import com.sarang.alarm.compose.AlarmScreen
import com.sarang.base_feed.ui.Feeds
import com.sarang.instagralleryModule.gallery.GalleryScreen
import com.sarang.profile.edit.EditProfileScreen
import com.sarang.profile.viewmodel.ProfileViewModel
import com.sarang.toringlogin.login.LoginScreen
import com.sr.restaurant.restaurant.compose.RestaurantScreen
import com.sryang.library.AddReviewScreen
import com.sryang.library.go
import com.sryang.myapplication.di.profile.ProfileScreen
import com.sryang.screenfindingtest.di.finding.Finding
import com.sryang.splash.compose.SplashScreen
import com.sryang.splash.uistate.LoginState
import dagger.hilt.android.AndroidEntryPoint
import kotlin.streams.toList

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val profileImageServerUrl = BuildConfig.PROFILE_IMAGE_SERVER_URL
    private val imageServerUrl = "http://sarang628.iptime.org:89/review_images/"
    private val restaurantServerUrl = "http://sarang628.iptime.org:89/restaurant_images/"

    private val profileViewmodel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Displaying edge-to-edge
        //WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navController = rememberNavController()
            TorangScreen(
                navController = navController,
                profileScreen = {
                    ProfileScreen(
                        id = it.arguments?.getString("id")?.toInt(),
                        isMyProfile = false,
                        imageServerUrl = imageServerUrl,
                        onEditProfile = { navController.navigate("editProfile") },
                        onSetting = { navController.navigate("settings") },
                        profileImageUrl = profileImageServerUrl
                    )
                },
                settings = {
                    SettingsScreen(onLogout = {
                        navController.go("splash")
                    }, onBack = {
                        navController.popBackStack()
                    })
                },
                splashScreen = {
                    SplashScreen(onLoginState = {
                        if (it == LoginState.LOGIN) {
                            navController.navigate("main") {
                                popUpTo(0)
                            }
                        } else if (it == LoginState.LOGOUT) {
                            navController.navigate("login") {
                                popUpTo(0)
                            }
                        }
                    })
                },
                addReviewScreen = {
                    val addReviewNavController = rememberNavController()
                    AddReviewScreen(
                        navController = addReviewNavController,
                        galleryScreen = { color, onNext, onClose ->
                            GalleryScreen(color, onNext, onClose)
                        },
                        onRestaurant = {
                            addReviewNavController.popBackStack()
                        },
                        onShared = {
                            navController.popBackStack()
                        }, onNext = {
                            addReviewNavController.navigate("addReview")
                        }
                    )
                },
                loginScreen = {
                    LoginScreen(
                        onLogin = { navController.go("main") },
                        onLogout = { navController.go("splash") }
                    )
                },
                restaurantScreen = { backStackEntry ->
                    val restaurantId = backStackEntry.arguments?.getString("restaurantId")
                    restaurantId?.let {
                        RestaurantScreen(
                            restaurantId = it.toInt(),
                            reviewImageUrl = "http://sarang628.iptime.org:89/review_images/",
                            restaurantImageUrl = "http://sarang628.iptime.org:89/restaurant_images/",
                            menuImageServerUrl = "http://sarang628.iptime.org:89/menu_images/",
                            feeds = {
                                Box(modifier = Modifier.background(colorResource(id = com.sarang.alarm.R.color.colorSecondaryLight))) {
                                    Feeds(
                                        list = ArrayList(it.stream().map { it.toFeedUiState() }
                                            .toList()),
                                        onProfile = { },
                                        onMenu = { },
                                        onImage = { },
                                        onName = {},
                                        onLike = { },
                                        onComment = { },
                                        onShare = { },
                                        onFavorite = { },
                                        onRestaurant = { },
                                        profileImageServerUrl = profileImageServerUrl,
                                        imageServerUrl = "http://sarang628.iptime.org:89/review_images/",
                                        isRefreshing = false,
                                        onRefresh = { },
                                    )
                                }
                            }
                        )
                    }
                },
                editProfileScreen = {
                    EditProfileScreen(
                        onEditImage = { navController.navigate("EditProfileImage") },
                        profileImageServerUrl = profileImageServerUrl,
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                },
                editProfileImageScreen = {
                    GalleryScreen(onNext = {
                        //profileViewModel.updateProfileImage(it[0]) // TODO:: profileViewModel 없이 이미지 업로드 방법 찾기
                        navController.popBackStack()
                    }, onClose = {
                        navController.popBackStack()
                    })
                },
                mainScreen = {
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
            )
        }
    }
}