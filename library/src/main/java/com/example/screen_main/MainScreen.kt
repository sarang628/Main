package com.example.screen_main

import RestaurantScreen
import SettingsScreen
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.screen_feed.FeedsScreen
import com.example.screen_feed.FeedsScreenInputEvents
import com.example.screen_feed.FeedsViewModel
import com.example.screen_finding.finding.TextFindScreen
import com.sarang.alarm.fragment.test
import com.sarang.alarm.uistate.testAlarmUiState
import com.sarang.profile.ProfileScreen
import com.sarang.profile.uistate.ProfileUiState
import com.sarang.profile.uistate.testProfileUiState
import com.sarang.screen_splash.SplashScreen
import com.sarang.toringlogin.login.LoginScreen
import com.sarang.toringlogin.login.LoginViewModel
import com.sryang.library.MainLogic
import com.sryang.library.SelectPictureAndAddReview
import com.sryang.torang_repository.services.RemoteReviewService
import com.sryang.torang_repository.services.impl.getLoginService
import com.sryang.torang_repository.session.SessionService
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import kotlin.streams.toList

@Composable
fun MainScreen(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    clickAddReview: ((Int) -> Unit)? = null,
    navController: NavHostController,
    mainLogic: MainLogic,
    feedsViewModel: FeedsViewModel,
    remoteReviewService: RemoteReviewService,
    loginViewModel: LoginViewModel
) {
    val profileUiState = testProfileUiState(lifecycleOwner)
    val alarmUiState = testAlarmUiState(context = context, lifecycleOwner)
    val context = LocalContext.current
    //mainLogic.start()
    val coroutine = rememberCoroutineScope()
    var isProgress by remember { mutableStateOf(false) }

    Column {
        NavHost(
            navController = navController, startDestination = "splash",
            modifier = Modifier.weight(1f)
        ) {
            composable("main") {
                MainScreen1(
                    context = context,
                    lifecycleOwner = lifecycleOwner,
                    clickAddReview = clickAddReview,
                    clickProfile = {
                        navController.navigate("profile")
                    },
                    clickShare = clickAddReview,
                    clickImage = clickAddReview,
                    clickRestaurant = {
                        navController.navigate("restaurant")
                    },
                    navController1 = navController, feedsViewModel = feedsViewModel
                )
            }
            composable("addReview") {
                //AddReview(0xFFFFFBE6)
                Box {
                    SelectPictureAndAddReview(
                        color = 0xFFFFFBE6,
                        onShare = {
                            //TODO viewmodel로 옮겨야 함.
                            coroutine.launch {
                                try {
                                    isProgress = true
                                    remoteReviewService.addReview(
                                        params = HashMap<String, RequestBody>().apply {
                                            put(
                                                "contents",
                                                it.contents.toRequestBody("text/plain".toMediaTypeOrNull())
                                            )
                                        },
                                        file = filesToMultipart(it.pictures)
                                    )
                                    isProgress = false
                                    navController.navigate("main")
                                } catch (e: Exception) {
                                    Log.d("MainActivity", e.toString())
                                    isProgress = false
                                }
                            }
                        })

                    if (isProgress) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                }
            }
            composable("restaurant") {
                RestaurantScreen()
            }

            composable("profile") {
                ProfileScreen(uiState = ProfileUiState(), onLogout =
                {
                    coroutine.launch {
                        Log.d("MainScreen", "!!!!!")
                        SessionService(context).removeToken()
                        navController.navigate("splash")
                    }
                }
                )
            }
            composable("splash") {
                Column {
                    SplashScreen(onSuccess = {
                        if (SessionService(context).isLogin.value)
                            navController.navigate("main")
                        else
                            navController.navigate("login")
                    })
                }
            }
            composable("login") {
                val flow = loginViewModel.uiState.collectAsState()
                LoginScreen(isLogin = flow.value.isLogin, onLogin = {
                    loginViewModel.login(it)
                    navController.navigate("main")
                }, onLogout = {
                    loginViewModel.logout()
                })
            }
            composable("settings") {
                SettingsScreen {

                }
            }
        }
    }
}


@Composable
fun MainScreen1(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    clickAddReview: ((Int) -> Unit)? = null,
    clickProfile: ((Int) -> Unit)? = null,
    clickShare: ((Int) -> Unit)? = null,
    clickImage: ((Int) -> Unit)? = null,
    clickRestaurant: ((Int) -> Unit)? = null,
    feedsViewModel: FeedsViewModel,
    navController1: NavController
) {
    val profileUiState = testProfileUiState(lifecycleOwner)
    val alarmUiState = testAlarmUiState(context = context, lifecycleOwner)
    val sessionService = SessionService(LocalContext.current)
    var isExpandMenuBottomSheet by remember { mutableStateOf(false) }
    var isExpandCommentBottomSheet by remember { mutableStateOf(false) }
    val coroutine = rememberCoroutineScope()
    Column {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = "profile",
            modifier = Modifier.weight(1f)
        ) {
            composable("profile") {
                FeedsScreen(
                    uiStateFlow = feedsViewModel.uiState,
                    inputEvents = FeedsScreenInputEvents(
                        onRefresh = {
                            feedsViewModel.refreshFeed()
                        },
                        onProfile = clickProfile,
                        onAddReview = clickAddReview,
                        onImage = clickImage,
                        onRestaurant = clickRestaurant,
                        onMenu = {
                            isExpandMenuBottomSheet = !isExpandMenuBottomSheet
                        },
                        onFavorite = { feedsViewModel.clickFavorite(it) },
                        onShare = clickShare,
                        onComment = {
                            isExpandCommentBottomSheet = !isExpandCommentBottomSheet
                        },
                        onLike = { feedsViewModel.clickLike(it) },
                        onName = clickProfile,
                    ),
                    imageServerUrl = "http://sarang628.iptime.org:89/review_images/",
                    profileImageServerUrl = "http://sarang628.iptime.org:89/profile_images/",
                    isExpandMenuBottomSheet = isExpandMenuBottomSheet,
                    isExpandCommentBottomSheet = isExpandCommentBottomSheet
                )
            }
            composable("friendslist") {
                val p by profileUiState.collectAsState()
                ProfileScreen(uiState = p, onLogout = {
                    coroutine.launch {
                        sessionService.removeToken()
                        navController1.navigate("splash")
                    }
                })
            }
            composable("finding") {
                TextFindScreen()
            }
            composable("alarm") {
                test(alarmUiState)
            }
            composable("addReview") {
                Text(text = "addReview")
            }
        }
        BottomNavigationComponent(navController = navController)
    }
}

@Composable
fun BottomNavigationComponent(navController: NavController) {
    var selectedIndex by remember { mutableStateOf(0) }
    val tabContents = listOf(
        "Feed" to Icons.Filled.Home,
        "Map" to Icons.Filled.AddCircle,
        "Alarm" to Icons.Filled.Settings,
        "Profile" to Icons.Filled.Settings
    )

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.colorSecondaryLight),
        contentColor = MaterialTheme.colors.onSurface,
        elevation = 2.dp
    ) {
        tabContents.forEachIndexed { index, pair: Pair<String, ImageVector> ->
            BottomNavigationItem(
                icon = { Icon(pair.second, contentDescription = null) },
                label = { androidx.compose.material.Text(pair.first) },
                selected = selectedIndex == index,
                alwaysShowLabel = false, // Hides the title for the unselected items
                onClick = {
                    selectedIndex = index
                    if (index == 0) {
                        navController.navigate("profile")
                    } else if (index == 1) {
                        navController.navigate("finding")
                    } else if (index == 2) {
                        navController.navigate("alarm")
                    } else {
                        navController.navigate("friendslist")
                    }
                }
            )
        }
    }
}

@Composable
@Preview
fun PreView() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationComponent(navController = navController) }
    ) { innerPaddingModifier ->
        Text(text = "", Modifier.padding(innerPaddingModifier))
        NavHost(navController = navController, startDestination = "profile") {
            composable("profile") {
                Text(text = "profile")
            }
            composable("finding") {
                Text(text = "finding")
            }
            composable("alarm") {
                Text(text = "alarm")
            }
            composable("friendslist") {
                Text(text = "friendslist")
            }
        }
    }
}

fun filesToMultipart(file: List<String>): ArrayList<MultipartBody.Part> {
    val list = ArrayList<MultipartBody.Part>()
        .apply {
            addAll(
                file.stream().map {
                    val file = File(it)
                    MultipartBody.Part.createFormData(
                        name = "file",
                        filename = file.name,
                        body = file.asRequestBody()
                    )
                }.toList()
            )
        }
    return list
}
