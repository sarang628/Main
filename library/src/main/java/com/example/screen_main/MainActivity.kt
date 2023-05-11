package com.example.screen_main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.screen_feed.uistate.getTestSenarioFeedFragmentUIstate
import com.sarang.profile.uistate.testProfileUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            val uiState = getTestSenarioFeedFragmentUIstate(this@MainActivity, this@MainActivity)
            val uiS = testProfileUiState(this@MainActivity)

            setContent {
                MainScreen(feedUiState = uiState, profileUiState = uiS, this@MainActivity)
            }

        }
    }
}