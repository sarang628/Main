package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.MainBottomNavigation
import com.sarang.torang.compose.MainBottomNavigationAppBar
import com.sarang.torang.di.main_di.ProvideMyFeedScreen
import com.sarang.torang.di.main_di.provideCommentBottomDialogSheet
import com.sarang.torang.di.main_di.provideMainScreen
import com.sarang.torang.repository.LoginRepository
import com.sryang.torang.ui.TorangTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var loginRepository: LoginRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TorangTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavigation()
                    //LoginRepositoryTest(loginRepository = loginRepository)
                    //MainBottomNavigationPreview()
                    //MainBottomAppBarPreview()
                }
            }
        }
    }
}

@Preview
@Composable
fun MainNavigation() {
    val navController = rememberNavController()
//                    Column(Modifier.verticalScroll(rememberScrollState())) {
//                        Box(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp.dp) - 40.dp)) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { provideMainScreen().invoke() }
        composable("modReview/{id}") { Text(text = "modReview ${it.arguments?.getString("id")}") }
        composable("profile/{id}") { Text(text = "profile ${it.arguments?.getString("id")}") }
        composable("restaurant/{id}") { Text(text = "restaurant ${it.arguments?.getString("id")}") }
        composable("addReview") { Text(text = "addReview") }
        composable("myFeed/{reviewId}") {
            val rootNavController = RootNavController()
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
    Scaffold(
        bottomBar = {
            MainBottomNavigationAppBar(
                navController = rememberNavController(),
                onAddReview = {})
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Blue)
            )
        }
    }
}