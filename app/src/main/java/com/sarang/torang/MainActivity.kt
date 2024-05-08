package com.sarang.torang

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sarang.torang.compose.MainScreen
import com.sarang.torang.di.bottomsheet.provideFeedMenuBottomSheetDialog
import com.sarang.torang.di.comment_di.provideCommentBottomDialogSheet
import com.sarang.torang.di.feed_di.provideFeedScreen
import com.sarang.torang.di.main_di.ProvideMyFeedScreen
import com.sarang.torang.di.profile_di.MyProfileScreenNavHost
import com.sarang.torang.di.report_di.provideReportModal
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.LoginRepositoryTest
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
                    var commentDialogShow by remember { mutableStateOf(false) }
                    var consumingBottomMenu by remember { mutableStateOf("") }
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        Box(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp.dp) - 40.dp)) {
                            NavHost(navController = navController, startDestination = "main") {
                                composable("main") {
                                    MainScreen(
                                        feedScreen = provideFeedScreen(
                                            progressTintColor = Color(0xffe6cc00),
                                            navController = navController,
                                            onAddReview = { navController.navigate("addReview") },
                                            onShowComment = { commentDialogShow = true },
                                            currentBottomMenu = consumingBottomMenu,
                                            onConsumeCurrentBottomMenu = {
                                                Log.d("__MainActivity", "consume feed bottom menu")
                                                consumingBottomMenu = ""
                                            }
                                        ),
                                        onBottomMenu = {
                                            Log.d("__MainActivity", "onBottomMenu:${it}")
                                            consumingBottomMenu = it
                                        },
                                        findingScreen = { Finding(navController = navController) },
                                        myProfileScreen = {
                                            val profileNavController =
                                                rememberNavController() // 상위에 선언하면 앱 죽음
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
                                                    ProvideMyFeedScreen(/*myProfileScreen*/
                                                        navController = navController,
                                                        reviewId = it.arguments?.getString("reviewId")
                                                            ?.toInt() ?: 0,
                                                        onEdit = { navController.navigate("modReview/${it}") },
                                                        onProfile = {
                                                            profileNavController.navigate(
                                                                "profile/${it}"
                                                            )
                                                        },
                                                        onBack = { profileNavController.popBackStack() }
                                                    )
                                                }
                                            )
                                        },
                                        alarm = { AlarmScreen(onEmailLogin = {}) },
                                        commentBottomSheet = provideCommentBottomDialogSheet(
                                            commentDialogShow
                                        ) {
                                            commentDialogShow = false
                                        },
                                        menuDialog = provideFeedMenuBottomSheetDialog(),
                                        shareDialog = provideShareBottomSheetDialog(),
                                        reportDialog = provideReportModal(),
                                        onEdit = { navController.navigate("modReview/${it}") }
                                    )
                                }
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
                                        reviewId = it.arguments?.getString("reviewId")?.toInt()
                                            ?: 0,
                                        onEdit = {}
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