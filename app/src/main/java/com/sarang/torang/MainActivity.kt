package com.sarang.torang

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AssistChip
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.di.finding_di.findingWithPermission
import com.sarang.torang.di.finding_di.rememberFindState
import com.sarang.torang.di.main_di.ProvideMyFeedScreen
import com.sarang.torang.di.main_di.provideCommentBottomDialogSheet
import com.sarang.torang.di.main_di.provideFeedGrid
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
                    MainNavHost()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    val rootNavController = RootNavController(navController)
    val findState = rememberFindState()
    val context : Context = LocalContext.current

    NavHost(navController = navController, startDestination = "main") {
        composable("main")              {
            BottomSheetScaffold(
                sheetContent = {
                    AssistChip({
                        context.startActivity(
                            Intent(
                                context,
                                TestActivity::class.java
                            )
                        )
                    }, label = {
                        Text("NewActivity")
                    })
                }
            ) {
                provideMainScreen(
                    rootNavController   = rootNavController,
                    find                = { findingWithPermission(findState = findState).invoke() },
                    feedGrid            = provideFeedGrid(),
                    findState           = findState,
                    showLog             = true
                ).invoke()
            }
        }
        composable("modReview/{id}")    { Text(text = "modReview ${it.arguments?.getString("id")}") }
        composable("profile/{id}")      { Text(text = "profile ${it.arguments?.getString("id")}") }
        composable("restaurant/{id}")   { Text(text = "restaurant ${it.arguments?.getString("id")}") }
        composable("addReview")         { Text(text = "addReview") }
        composable("myFeed/{reviewId}") {
            ProvideMyFeedScreen(
                navController       = navController,
                rootNavController   = rootNavController,
                navBackStackEntry   = it,
                videoPlayer         = { url, isPlaying, onVideoClick -> },
                commentBottomSheet  = { provideCommentBottomDialogSheet(rootNavController).invoke(it) }
            )
        }
    }
}