package com.example.screen_main

import android.content.Context
import androidx.compose.foundation.layout.Column
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
    mainNavigation: String,
    clickAddReview: ((Int) -> Unit)? = null,

    ) {
    val navController = rememberNavController()
    val profileUiState = testProfileUiState(lifecycleOwner)
    val alarmUiState = testAlarmUiState(context = context, lifecycleOwner)

    if (mainNavigation == MainNavigation.ADD_REVIEW)
        Column() {
            Text(text = "addReview")
        }

    if (mainNavigation == MainNavigation.MAIN)
        Column {
            NavHost(
                navController = navController, startDestination = "profile",
                modifier = Modifier.weight(1f)
            ) {
                composable("profile") {
                    val viewModel = FeedsViewModel(context)
                    FeedsScreen(
                        feedsViewModel = viewModel,
                        onRefresh = { viewModel.refresh() },
                        clickProfile = { viewModel.clickProfile() },
                        clickAddReview = clickAddReview,
                        clickImage = { viewModel.clickImage() },
                        clickRestaurant = { viewModel.clickRestaurant() },
                        onMenuClickListener = { viewModel.clickMenu() },
                        onClickFavoriteListener = { viewModel.clickFavorite() },
                        onShareClickListener = { viewModel.clickShare() },
                        onCommentClickListener = { viewModel.clickComment() },
                        onLikeClickListener = { viewModel.clickLike() },
                        onRestaurantClickListener = { viewModel.clickRestaurant() },
                        onNameClickListener = { viewModel.clickName() }
                    )
                }
                composable("friendslist") {
                    val p by profileUiState.collectAsState()
                    ProfileScreen(uiState = p)
                }
                composable("finding") {
                    TextFindScreen(context)
                }
                composable("alarm") {
                    test(alarmUiState)
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