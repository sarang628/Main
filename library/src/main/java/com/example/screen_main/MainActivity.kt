package com.example.screen_main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.example.screen_feed.uistate.getTestSenarioFeedFragmentUIstate
import com.sarang.profile.uistate.testProfileUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            val uiState = getTestSenarioFeedFragmentUIstate(this@MainActivity, this@MainActivity)
            val uiS = testProfileUiState(this@MainActivity)

            setContent {
                MainScreen(feedUiState = uiState, profileUiState = uiS)
            }

        }
    }
}

@Composable
fun test() {
    var name by remember { mutableStateOf("") }
}