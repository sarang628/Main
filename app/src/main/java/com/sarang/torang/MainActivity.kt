package com.sarang.torang

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sarang.torang.compose.MainDialogs
import com.sarang.torang.compose.MainScreen
import com.sarang.torang.compose.feed.Feed
import com.sarang.torang.compose.feed.MainFeedScreen
import com.sarang.torang.compose.feed.MyFeedScreen
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.di.bottomsheet.provideFeedMenuBottomSheetDialog
import com.sarang.torang.di.comment_di.provideCommentBottomDialogSheet
import com.sarang.torang.di.feed_di.toReview
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.di.profile_di.MyProfileScreenNavHost
import com.sarang.torang.di.report_di.provideReportModal
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.LoginRepositoryTest
import com.sarang.torang.viewmodels.FeedDialogsViewModel
import com.sryang.findinglinkmodules.di.finding_di.Finding
import com.sryang.torang.compose.AlarmScreen
import com.sryang.torangbottomsheet.di.bottomsheet.provideShareBottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var loginRepository: LoginRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TorangTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        Box(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp.dp) - 40.dp)) {
                            NavHost(navController = navController, startDestination = "main") {
                                composable(
                                    "main",
                                    content = provideMainScreen(navController = navController)
                                )
                                composable("modReview/{id}") {
                                    Text(text = "modReview ${it.arguments?.getString("id")}")
                                }
                                composable("profile/{id}") {
                                    Text(text = "profile ${it.arguments?.getString("id")}")
                                }
                                composable("restaurant/{id}") {
                                    Text(text = "restaurant ${it.arguments?.getString("id")}")
                                }
                                composable("addReview") {
                                    Text(text = "addReview")
                                }
                                composable("myFeed/{reviewId}") {
                                    ProvideMyFeedScreen(
                                        navController = navController,
                                        navBackStackEntry = it
                                    )
                                }
                            }
                        }
                        LoginRepositoryTest(loginRepository = loginRepository)
                    }
                }
            }
        }
    }
}

fun provideMainScreen(
    navController: NavHostController,
): @Composable (NavBackStackEntry) -> Unit = {
    val dialogsViewModel: FeedDialogsViewModel = hiltViewModel()
    ProvideMainDialog(
        dialogsViewModel = dialogsViewModel,
        navController = navController
    ) {
        MainScreen(
            feedScreen = { onComment, onMenu, onShare, navBackStackEntry ->
                MainFeedScreen(
                    onAddReview = { navController.navigate("addReview") },
                    feed = provideFeed(onComment, onMenu, onShare, navController),
                    consumeOnTop = {},
                    onTop = false
                )
            },
            onBottomMenu = {
                Log.d("__MainActivity", "onBottomMenu:${it}")
            },
            findingScreen = { Finding(navController = RootNavController()) },
            myProfileScreen = {
                val profileNavController = rememberNavController() // 상위에 선언하면 앱 죽음
                MyProfileScreenNavHost(
                    navController = profileNavController,
                    onSetting = { navController.navigate("settings") },
                    onEmailLogin = { navController.navigate("emailLogin") },
                    onReview = {
                        Log.d(
                            "__Main",
                            "MyProfileScreen onReview reviewId : ${it}"
                        )
                        profileNavController.navigate("myFeed/${it}")
                    },
                    onClose = { profileNavController.popBackStack() },
                    myFeed = {
                        ProvideMyFeedScreen(navController = navController, navBackStackEntry = it)
                    }
                )
            },
            alarm = { AlarmScreen(onEmailLogin = {}) },
            onComment = { dialogsViewModel.onComment(it) },
            onMenu = { dialogsViewModel.onMenu(it) },
            onShare = { dialogsViewModel.onShare(it) }
        )
    }
}

@Composable
fun ProvideMainDialog(
    dialogsViewModel: FeedDialogsViewModel = hiltViewModel(),
    navController: NavHostController,
    contents: @Composable () -> Unit,
) {

    val uiState by dialogsViewModel.uiState.collectAsState()
    val onEdit: (Int) -> Unit = {
        navController.navigate("modReview/${it}")
    }
    MainDialogs(
        uiState = uiState,
        commentBottomSheet = { provideCommentBottomDialogSheet().invoke(it) { dialogsViewModel.closeComment() } },
        menuDialog = provideFeedMenuBottomSheetDialog(),
        shareDialog = provideShareBottomSheetDialog(),
        reportDialog = provideReportModal(),
        onEdit = {
            navController.navigate("modReview/${it}")
        },
        contents = contents
    )
}

fun provideFeed(
    onComment: ((Int) -> Unit),
    onMenu: ((Int) -> Unit),
    onShare: ((Int) -> Unit),
    navController: NavHostController,
): @Composable (Feed) -> Unit = {
    Feed(
        review = it.toReview(),
        isZooming = { /*scrollEnabled = !it*/ },
        progressTintColor = Color(0xFF000000),
        image = provideTorangAsyncImage(),
        onComment = { onComment.invoke(it.reviewId) },
        onShare = { onShare.invoke(it.reviewId) },
        onMenu = { onMenu.invoke(it.reviewId) },
        onName = { navController.navigate("profile/${it.userId}") },
        onRestaurant = { navController.navigate("restaurant/${it.restaurantId}") },
        onImage = { },
        onProfile = { navController.navigate("profile/${it.userId}") }
    )
}

@Composable
fun ProvideMyFeedScreen(
    dialogsViewModel: FeedDialogsViewModel = hiltViewModel(),
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry,
) {
    ProvideMainDialog(
        dialogsViewModel = dialogsViewModel,
        navController = navController
    ) {
        MyFeedScreen(
            reviewId = navBackStackEntry.arguments?.getString("reviewId")?.toInt()
                ?: 0,
            listState = rememberLazyListState(),
            feed = {
                Feed(
                    review = it.toReview(),
                    isZooming = { /*scrollEnabled = !it*/ },
                    progressTintColor = Color(0xFF000000),
                    image = provideTorangAsyncImage(),
                    onComment = { dialogsViewModel.onComment(it.reviewId) },
                    onShare = { dialogsViewModel.onShare(it.reviewId) },
                    onMenu = { dialogsViewModel.onMenu(it.reviewId) },
                    onName = { navController.navigate("profile/${it.userId}") },
                    onRestaurant = { navController.navigate("restaurant/${it.restaurantId}") },
                    onImage = { },
                    onProfile = { }
                )
            }
        )
    }
}