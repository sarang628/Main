package com.example.screen_main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.screen_feed.uistate.getTestSenarioFeedFragmentUIstate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            val uiState =
                getTestSenarioFeedFragmentUIstate(this@MainActivity, this@MainActivity)
            uiState.collect { uiState ->
                setContent { MainScreen(uiState) }
            }
        }
    }
}