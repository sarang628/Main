package com.example.screen_main

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.screen_feed.FeedsScreen
import com.example.screen_feed.FeedsViewModel
import com.example.screen_finding.finding.TextFindScreen
import com.sarang.alarm.fragment.test
import com.sarang.alarm.uistate.testAlarmUiState
import com.sarang.profile.ProfileScreen
import com.sarang.profile.uistate.testProfileUiState

@Composable
fun MainScreen(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    clickAddReview: ((Int) -> Unit)? = null,
    navController: NavHostController
) {
    val profileUiState = testProfileUiState(lifecycleOwner)
    val alarmUiState = testAlarmUiState(context = context, lifecycleOwner)

    Column {
        NavHost(
            navController = navController, startDestination = "main",
            modifier = Modifier.weight(1f)
        ) {
            composable("main") {
                MainScreen1(
                    context = context,
                    lifecycleOwner = lifecycleOwner,
                    clickAddReview = clickAddReview,
                    clickProfile = clickAddReview,
                    clickComment = clickAddReview,
                    clickShare = clickAddReview,
                    clickImage = clickAddReview,
                    clickRestaurant = clickAddReview
                )
            }
            composable("addReview") {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(id = R.color.colorSecondaryLight))
                ) {
                    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "준비중입니다.")
                    }
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
    clickComment: ((Int) -> Unit)? = null,
    clickShare: ((Int) -> Unit)? = null,
    clickImage: ((Int) -> Unit)? = null,
    clickRestaurant: ((Int) -> Unit)? = null
) {
    val profileUiState = testProfileUiState(lifecycleOwner)
    val alarmUiState = testAlarmUiState(context = context, lifecycleOwner)
    Column {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = "profile",
            modifier = Modifier.weight(1f)
        ) {
            composable("profile") {
                val viewModel = FeedsViewModel(context)
                FeedsScreen(
                    feedsViewModel = viewModel,
                    onRefresh = { viewModel.refresh() },
                    clickProfile = clickProfile,
                    clickAddReview = clickAddReview,
                    clickImage = clickImage,
                    clickRestaurant = { viewModel.clickRestaurant() },
                    onMenuClickListener = { viewModel.clickMenu() },
                    onClickFavoriteListener = { viewModel.clickFavorite() },
                    onShareClickListener = clickShare,
                    onCommentClickListener = clickComment,
                    onLikeClickListener = { viewModel.clickLike() },
                    onRestaurantClickListener = clickRestaurant,
                    onNameClickListener = { viewModel.clickName() }
                )
            }
            composable("friendslist") {
                val p by profileUiState.collectAsState()
                ProfileScreen(uiState = p)
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
fun preView() {
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