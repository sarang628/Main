package com.example.screen_main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.screen_feed.uistate.getTestSenarioFeedFragmentUIstate
import com.sarang.alarm.uistate.testAlarmUiState
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
            val test = testAlarmUiState(this@MainActivity, this@MainActivity)
            setContent {
                MainScreen(feedUiState = uiState, profileUiState = uiS, alarmUiState = test, this@MainActivity)
            }

        }
    }
}