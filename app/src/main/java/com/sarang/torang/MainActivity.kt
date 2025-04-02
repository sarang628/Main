package com.sarang.torang

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sarang.torang.compose.MainBottomNavigation
import com.sarang.torang.compose.MainBottomNavigationAppBar
import com.sarang.torang.di.main_di.ProvideMyFeedScreen
import com.sarang.torang.di.main_di.provideCommentBottomDialogSheet
import com.sarang.torang.di.main_di.provideMainScreen
import com.sarang.torang.repository.LoginRepository
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
//                    Column(Modifier.verticalScroll(rememberScrollState())) {
//                        Box(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp.dp) - 40.dp)) {
                    NavHost(navController = navController, startDestination = "main") {
                        composable(
                            "main",
                            content = { it ->
                                val rootNavController = RootNavController()
                                provideMainScreen(
                                    rootNavController,
                                    videoPlayer = { url, isPlaying, onVideoClick -> },
                                    addReviewScreen = {},
                                    chat = {},
                                    onCloseReview = {},
                                    onMessage = {}
                                ).invoke()

                            }
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
                            val rootNavController = RootNavController()
                            ProvideMyFeedScreen(
                                navController = navController,
                                rootNavController = rootNavController,
                                navBackStackEntry = it,
                                videoPlayer = { url, isPlaying, onVideoClick -> },
                                commentBottomSheet = { reviewId, onHidden, contents ->
                                    provideCommentBottomDialogSheet(rootNavController).invoke(
                                        reviewId,
                                        onHidden,
                                        contents
                                    )
                                }
                            )
                        }
                    }
                }
                //LoginRepositoryTest(loginRepository = loginRepository)
                //}
//                    }
//                }
            }
        }
    }
}


@Preview
@Composable
fun MainBottomNavigationPreview() {
    TorangTheme {
        MainBottomNavigation(navController = rememberNavController())
    }
}

@Preview
@Composable
fun MainBottomAppBarPreview() {
    TorangTheme {
        MainBottomNavigationAppBar(navController = rememberNavController(), onAddReview = {})
    }
}