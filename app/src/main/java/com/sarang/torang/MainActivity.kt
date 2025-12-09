package com.sarang.torang

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.screen_finding.ui.Buttons
import com.sarang.torang.di.finding_di.rememberFindState
import com.sarang.torang.di.main_di.ProvideMyFeedScreen
import com.sarang.torang.di.main_di.provideMainScreen
import com.sarang.torang.di.profile_di.MyProfileScreenNavHost
import com.sarang.torang.di.restaurant_detail_container_di.provideRestaurantDetailContainer
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.test.LoginRepositoryTest
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
                    MainNavHost(loginRepository = loginRepository)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MainNavHost(loginRepository: LoginRepository) {
    val navController = rememberNavController()
    val rootNavController = RootNavController(navController)
    val findState = rememberFindState()
    val context : Context = LocalContext.current

    NavHost(navController = navController, startDestination = "menu") {
        composable("menu"){
            Scaffold {
                Column(Modifier.padding(it)) {
                    Button({navController.navigate("main")}) { Text("Main") }
                    Button({navController.navigate("LoginRepositoryTest")}) { Text("LoginRepositoryTest") }
                }
            }

        }
        composable("main")              {
            BottomSheetScaffold(
                sheetContent = {
                    AssistChip(onClick  = { context.startActivity(
                                            Intent(context,
                                                    TestActivity::class.java)) },
                               label    = { Text("NewActivity") })
                },
                sheetPeekHeight = 5.dp ) {
                provideMainScreen(
                    rootNavController   = rootNavController,
                    findState           = findState,
                    showLog             = true,
                    myProfileScreen = {
                        MyProfileScreenNavHost()
                    },
                    findScreen = {},
                    alarmScreen = {},
                    chatScreen = {},
                    addReviewScreenType = {}
                ).invoke()
            }
        }
        composable("modReview/{id}")    { Text(text = "modReview ${it.arguments?.getString("id")}") }
        composable("profile/{id}")      { Text(text = "profile ${it.arguments?.getString("id")}") }
        composable("restaurant/{id}")   {
            Text(text = "restaurant ${it.arguments?.getString("id")}")
            it.arguments?.getString("id")?.toInt()?.let {
                provideRestaurantDetailContainer(rootNavController = rootNavController).invoke(it)
            }
        }
        composable("addReview")         { Text(text = "addReview") }
        composable("myFeed/{reviewId}") {
            ProvideMyFeedScreen(
                navController       = navController,
                rootNavController   = rootNavController,
                navBackStackEntry   = it
            )
        }
        composable("LoginRepositoryTest"){
            LoginRepositoryTest(loginRepository)
        }
    }
}