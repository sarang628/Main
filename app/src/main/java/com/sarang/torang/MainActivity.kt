package com.sarang.torang

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.MainBottomNavigation
import com.sarang.torang.compose.MainBottomNavigationBar
import com.sarang.torang.di.main_di.ProvideMyFeedScreen
import com.sarang.torang.di.main_di.provideCommentBottomDialogSheet
import com.sarang.torang.di.main_di.provideMainScreen
import com.sarang.torang.repository.LoginRepository
import com.sryang.torang.ui.TorangTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var loginRepository: LoginRepository

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TorangTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainNavigation()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val rootNavController = RootNavController(navController)
    val currentState = navController.currentBackStackEntryFlow
        .collectAsStateWithLifecycle(initialValue = null)

    NavHost(navController = navController, startDestination = "main") {
        composable("main")              { provideMainScreen(rootNavController).invoke() }
        composable("modReview/{id}")    { Text(text = "modReview ${it.arguments?.getString("id")}") }
        composable("profile/{id}")      { Text(text = "profile ${it.arguments?.getString("id")}") }
        composable("restaurant/{id}")   { Text(text = "restaurant ${it.arguments?.getString("id")}") }
        composable("addReview")         { Text(text = "addReview") }
        composable("myFeed/{reviewId}") {
            ProvideMyFeedScreen(
                navController = navController,
                rootNavController = rootNavController,
                navBackStackEntry = it,
                videoPlayer = { url, isPlaying, onVideoClick -> },
                commentBottomSheet = { reviewId, onHidden, content ->
                    provideCommentBottomDialogSheet(rootNavController).invoke(
                        reviewId,
                        onHidden,
                        content
                    )
                }
            )
        }
    }
}