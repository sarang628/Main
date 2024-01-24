package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sarang.torang.di.main_di.ProvideMainScreen
import com.sryang.torang_repository.repository.LoginRepository
import com.sryang.torang_repository.repository.LoginRepositoryTest
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var loginRepository: LoginRepository

    @OptIn(ExperimentalMaterial3Api::class)
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
                                composable("main") {
                                    ProvideMainScreen(
                                        navController = navController,
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
                            }
                        }
                        LoginRepositoryTest(loginRepository = loginRepository)
                    }
                }
            }
        }
    }
}